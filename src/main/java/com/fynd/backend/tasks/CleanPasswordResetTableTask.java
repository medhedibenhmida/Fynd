package com.fynd.backend.tasks;

import com.fynd.backend.repository.PasswordResetTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class CleanPasswordResetTableTask {

    private final PasswordResetTokenRepository repository;
    private final String scriptName = "CleanPasswordResetToken"; // nom du script
    private final String basePath = "logs/client_data_base"; // dossier racine sous logs

    public CleanPasswordResetTableTask(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    // Exécution tous les jours à minuit
    @Scheduled(cron = "0 0 0 * * *") // seconde=0, minute=0, heure=0 → tous les jours à minuit
    @Transactional
    public void clean() {
        int deletedCount = 0;

        try {
            deletedCount = repository.deleteExpired(); // supprime uniquement les tokens expirés
        } catch (Exception e) {
            writeLog(null, "Erreur lors du nettoyage : " + e.getMessage());
            e.printStackTrace();
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        // Nom du mois en toutes lettres (français)
        String monthName = now.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH).toLowerCase();

        // Création du dossier complet : logs/client_data_base/<script>/<mois>
        File dir = new File(basePath + "/" + scriptName + "/" + monthName);
        if (!dir.exists()) {
            boolean ok = dir.mkdirs();
            if (!ok) {
                System.out.println("Impossible de créer le dossier : " + dir.getAbsolutePath());
            } else {
                System.out.println("Dossier créé : " + dir.getAbsolutePath());
            }
        }

        // Nom du fichier avec date et heure de l’exécution
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File logFile = new File(dir, "log_" + timestamp + ".txt");

        String message = String.format(
                "Nettoyage table PasswordResetToken effectué à %s, %d token(s) supprimé(s)",
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                deletedCount
        );

        System.out.println(message);
        writeLog(logFile, message);
    }

    private void writeLog(File file, String message) {
        if (file == null) return;
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
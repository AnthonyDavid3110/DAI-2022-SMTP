package ch.heigvd.dai;
import ch.heigvd.dai.config.ConfigurationManager;
import ch.heigvd.dai.model.prank.Prank;
import ch.heigvd.dai.model.prank.PrankGenerator;
import ch.heigvd.dai.smtp.SmtpClient;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anthony David
 * @author Stéphane Nascimento Santos
 * @version 1.0
 */
public class App
{
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    /**
     * Main fonction of the application
     * @param args of the app (not used in this app)
     */
    public static void main( String[] args )
    {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        try {
            LOG.log(Level.INFO, "Beginning of the program.");
            final ConfigurationManager cm = new ConfigurationManager();
            final SmtpClient client = new SmtpClient(cm.getSmtpServerAddr(), cm.getSmtpServerPort());
            final PrankGenerator prankGenerator = new PrankGenerator(cm);
            final List<Prank> pranks = prankGenerator.generatePranks();
            LOG.log(Level.INFO, "Sending mails pranks");


            // Création des pranks et envoie
            for (Prank p : pranks) {
                client.sendMessage(p.generateMessage());
            }
            LOG.log(Level.INFO, "End of the program.");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
}
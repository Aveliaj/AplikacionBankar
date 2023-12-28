import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AplikacionBankarUI {
    private static double bilanci = 0;
    private static List<String> historiaTransaksioneve = new ArrayList<>();
    private static final DecimalFormat formatDecimale = new DecimalFormat("#.##");

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplikacioni Bankar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        frame.add(panel);
        vendosKomponentet(panel);

        frame.setVisible(true);
    }

    private static void vendosKomponentet(JPanel panel) {
        panel.setLayout(null);

        JLabel etiketaBilancit = new JLabel("Bilanci: $" + formatDecimale.format(bilanci));
        etiketaBilancit.setBounds(10, 20, 200, 25);
        panel.add(etiketaBilancit);

        JTextField fushaShumes = new JTextField(10);
        fushaShumes.setBounds(10, 50, 150, 25);
        panel.add(fushaShumes);

        JButton butoniDepozite = new JButton("Depozitë");
        butoniDepozite.setBounds(10, 80, 100, 25);
        panel.add(butoniDepozite);

        JButton butoniTerheqje = new JButton("Terheqje");
        butoniTerheqje.setBounds(120, 80, 100, 25);
        panel.add(butoniTerheqje);

        JButton butoniTransferimi = new JButton("Transferim");
        butoniTransferimi.setBounds(230, 80, 100, 25);
        panel.add(butoniTransferimi);

        JTextArea zonaHistorise = new JTextArea();
        zonaHistorise.setBounds(10, 120, 450, 100);
        zonaHistorise.setEditable(false);
        panel.add(zonaHistorise);

        butoniDepozite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kryejTransaksionin(fushaShumes, LlojiTransaksionit.DEPOZITE, etiketaBilancit, zonaHistorise);
            }
        });

        butoniTerheqje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kryejTransaksionin(fushaShumes, LlojiTransaksionit.TERHEQJE, etiketaBilancit, zonaHistorise);
            }
        });

        butoniTransferimi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kryejTransaksionin(fushaShumes, LlojiTransaksionit.TRANSFERIM, etiketaBilancit, zonaHistorise);
            }
        });
    }

    private static void kryejTransaksionin(JTextField fushaShumes, LlojiTransaksionit lloji, JLabel etiketaBilancit, JTextArea zonaHistorise) {
        try {
            double shuma = Double.parseDouble(fushaShumes.getText());
            String mesazhi = "";

            switch (lloji) {
                case DEPOZITE:
                    if (shuma > 0) {
                        bilanci += shuma;
                        mesazhi = "Depozituar: $" + formatDecimale.format(shuma);
                    } else {
                        mesazhi = "Shuma e pavlefshme. Ju lutem vendosni një numër pozitiv.";
                    }
                    break;
                case TERHEQJE:
                    if (shuma > 0 && shuma <= bilanci) {
                        bilanci -= shuma;
                        mesazhi = "Terhequr: $" + formatDecimale.format(shuma);
                    } else {
                        mesazhi = "Shuma e pavlefshme ose balanci i pamjaftueshëm.";
                    }
                    break;
                case TRANSFERIM:
                    // Operacion transferimi i simuluar për qëllime demonstruese
                    double shumaTransferuar = shuma;
                    if (shuma > 0 && shuma <= bilanci) {
                        bilanci -= shumaTransferuar;
                        mesazhi = "Transferuar: $" + formatDecimale.format(shumaTransferuar) + " në llogari tjetër";
                    } else {
                        mesazhi = "Shuma e pavlefshme ose balanci i pamjaftueshëm për transferim.";
                    }
                    break;
            }

            historiaTransaksioneve.add(mesazhi);
            azhurnoHistorine(zonaHistorise);
            etiketaBilancit.setText("Bilanci: $" + formatDecimale.format(bilanci));
            fushaShumes.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ju lutem vendosni një shumë të vlefshme.");
        }
    }

    private static void azhurnoHistorine(JTextArea zonaHistorise) {
        StringBuilder historia = new StringBuilder("Historia e Transaksioneve:\n");
        for (String transaksion : historiaTransaksioneve) {
            historia.append(transaksion).append("\n");
        }
        zonaHistorise.setText(historia.toString());
    }

    private enum LlojiTransaksionit {
        DEPOZITE, TERHEQJE, TRANSFERIM
    }
}

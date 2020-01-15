import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private World world = null;

    public void display(World w) {
        world = w;
        repaint();
    }

    protected void paintComponent(java.awt.Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (world != null) {
            int min = (int) Math.min((getWidth() / world.getWidth()), (getHeight() / world.getHeight()));
            for (int i = 0; i < world.getHeight(); i++) {
                for (int j = 0; j < world.getWidth(); j++) {

                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(i * min, j * min, min, min);

                    if (this.world.getCell(j, i)) {
                        g.setColor(Color.BLACK);
                        g.fillRect(i * min, j * min, min, min);
                    }
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("Generation - " + this.world.getGenerationCount(), 10, this.getHeight());
        }
    }
}

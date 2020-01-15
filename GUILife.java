import java.util.Timer;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.*;

public class GUILife extends JFrame implements ListSelectionListener {
    private World world;
    private PatternStore store;
    private ArrayList<World> cachedWorlds;
    private GamePanel gamePanel;
    private JButton back;
    private JButton play;
    private JButton forward;
    private java.util.Timer timer;
    private boolean playing;

    public GUILife(PatternStore ps) throws Exception {
        super("Game of Life");
        store = ps;
        cachedWorlds = new ArrayList<>();
        playing = false;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {
        gamePanel = new GamePanel();
        addBorder(gamePanel, "Game Panel");
        return gamePanel;
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        addBorder(patt, "Patterns");
        Pattern[] patternsArray = store.getPatterns();
        patt.setLayout(new BorderLayout());
        JList<Pattern> patterns = new JList<>(patternsArray);
        JScrollPane scroll = new JScrollPane(patterns);
        patt.add(scroll);
        patterns.addListSelectionListener(this);
        return patt;
    }

    private JPanel createControlPanel() throws Exception {
        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
        ctrl.setLayout(new GridLayout(1, 3, 1, 0));
        back = new JButton("Back");
        play = new JButton("Play");
        forward = new JButton("Forward");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (world != null) {
                    if (world.getGenerationCount() > 0) {
                        moveBack();
                        gamePanel.display(world);
                    } else {
                        gamePanel.display(world);
                    }
                }
            }
        });
        ctrl.add(back);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (world != null) {
                    runOrPause();
                    gamePanel.display(world);
                }
            }
        });
        ctrl.add(play);

        forward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (world != null) {
                    moveForward();
                    gamePanel.display(world);
                }
            }
        });
        ctrl.add(forward);
        return ctrl;
    }

    public void valueChanged(ListSelectionEvent e) {
        JList<Pattern> list = (JList<Pattern>) e.getSource();
        Pattern p = list.getSelectedValue();
        if (playing) {
            runOrPause();
        }
        if (p.getWidth() * p.getHeight() <= 64) {
            world = new PackedWorld(p);
        } else {
            world = new ArrayWorld(p);
        }
        cachedWorlds.clear();
        World world1 = copyWorld(true);
        cachedWorlds.add(world1);
        gamePanel.display(world);
    }

    /**
     * @param useCloning indicates whether world should be copied using the clone method or the corresponding copy constructor
     * @return a deep copy of world
     */
    private World copyWorld(boolean useCloning) {
        if (useCloning) {
            World copy = (World) world.clone();
            return copy;
        }
        // if useCloning is false
        if (world instanceof ArrayWorld) {
            World copy = new ArrayWorld((ArrayWorld) world);
            return copy;
        }
        // world is instanceof PackedWorld
        World copy = new PackedWorld((PackedWorld) world);
        return copy;
    }

    /** Moves back one generation. If it is already at generation 0, assigns the 0th generation to world. */
    private void moveBack() {
        if (world != null) {
            if (world.getGenerationCount() != 0) {
                world.decrementGenerationCount();
                world = cachedWorlds.get(world.getGenerationCount());
                world = copyWorld(true);
            } else {
                world = cachedWorlds.get(0);
            }
        }

        gamePanel.display(world);
    }

    /** Moves forward one generation. */
    private void moveForward() {
        if (world != null) {
            if (world.getGenerationCount() + 1 < cachedWorlds.size()) {
                world.incrementGenerationCount();
                world = cachedWorlds.get(world.getGenerationCount());
                world = copyWorld(true);
            } else {
                world.nextGeneration();
                World world1 = copyWorld(true);
                cachedWorlds.add(world1);
            }
            gamePanel.display(world);
        }
    }

    private void runOrPause() {
        if (playing) {
            timer.cancel();
            playing = false;
            play.setText("Play");
        } else {
            playing = true;
            play.setText("Stop");
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 100);
        }
    }

    public static void main(String[] args) throws Exception {
        PatternStore ps = new PatternStore("https://bit.ly/2FJERFh");
        GUILife gui = new GUILife(ps);
        gui.setVisible(true);
    }

}

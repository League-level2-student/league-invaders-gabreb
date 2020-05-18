import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont;
	Font titleFontEnter;
	Timer frameDraw;
	Rocketship rocketship = new Rocketship(225, 698, 50, 50);
	ObjectManager manager = new ObjectManager(rocketship);
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	Timer alienSpawn;
	GamePanel() {
		titleFont = new Font("Arial", Font.PLAIN, 48);
		titleFontEnter = new Font("Arial", Font.PLAIN, 20);
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		GamePanel Panel = new GamePanel();
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}

	}

	void updateMenuState() {
	}

	void updateGameState() {
		manager.update();
	}

	void updateEndState() {
	}

	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("LEAGUE INVADERS", 25, 125);
		g.setFont(titleFontEnter);
		g.drawString("Press ENTER to start", 150, 350);
		g.setFont(titleFontEnter);
		g.drawString("Press SPACE for instructions", 120, 550);
	}

	void drawGameState(Graphics g) {
		if (needImage) {
		    loadImage ("space.png");
		}
		if (gotImage) {
			g.drawImage(image, 0, 0, LeagueInvaders.WIDTH,LeagueInvaders.HEIGHT, null);
		} else {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		}
		manager.draw(g);
	}

	void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Game Over", 120, 125);
		g.setFont(titleFontEnter);
		g.drawString("You killed Enemies", 160, 350);
		g.setFont(titleFontEnter);
		g.drawString("Press Enter to Restart", 150, 550);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("action");
		repaint();
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentState==GAME&& e.getKeyCode() == KeyEvent.VK_SPACE) {
			manager.addProjectile(rocketship.getProjectile());
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
			} else {
				currentState++;
				if (currentState == GAME) {
					startGame();
				}
				if (currentState==END) {
					alienSpawn.stop();
				}
			}
		}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (rocketship.y <= 0) {
				} else {
					rocketship.up();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (rocketship.y >= 730) {
				} else {
					rocketship.down();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (rocketship.x <=25) {}
				else {rocketship.left();}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (rocketship.x >=420) {}
				else {rocketship.right();}
			}
		}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
	void startGame() {
		alienSpawn = new Timer(1000, manager);
	    alienSpawn.start();
	}

}

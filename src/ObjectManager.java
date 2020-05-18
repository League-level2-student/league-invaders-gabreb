import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener{
	Rocketship rocket;
	ArrayList<Projectile> PJ = new ArrayList<Projectile>();
	ArrayList<Alien> Alien = new ArrayList<Alien>();
	Random randy = new Random();

	ObjectManager(Rocketship rocket) {
		this.rocket = rocket;
	}

	void addProjectile(Projectile Pro) {
		PJ.add(Pro);
	}

	void addAlien() {
		Alien.add(new Alien(randy.nextInt(LeagueInvaders.WIDTH), 0, 50, 50));
	}

	void update() {
		for (Alien a : Alien) {
			a.update();
			System.out.println(a.y);
			if (a.y > LeagueInvaders.HEIGHT) {
				a.isActive = false;
			}
		}
		for (Projectile p : PJ) {
			p.update();
			System.out.println(p.y);
			if (p.y < 0) {
				p.isActive = false;
			}
		}
		
		checkCollision();
		purgeObjects();
	}

	void draw(Graphics g) {
		rocket.draw(g);
		for (Alien a : Alien) {
			a.draw(g);
		}
		for (Projectile p : PJ) {
			p.draw(g);
		}
	}

	void purgeObjects() {
		for (int i = 0; i < Alien.size(); i++) {
			if (Alien.get(i).isActive == false) {
				Alien.remove(i);
			}
		}
		for (int z = 0; z < PJ.size(); z++) {
			if (PJ.get(z).isActive == false) {
				PJ.remove(z);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		addAlien();
	}

	void checkCollision() {
		for (int i = 0; i < Alien.size(); i++) {
			for (int a = 0; a < PJ.size(); a++) {
				if (PJ.get(i).collisionBox.intersects(Alien.get(i).collisionBox)) {
					PJ.get(i).isActive = false;
					Alien.get(i).isActive = false;
					if (rocket.collisionBox.intersects(Alien.get(i).collisionBox)) {
						Alien.get(i).isActive = false;
						rocket.isActive = false;
					}
				}
			}
		}
	}
}

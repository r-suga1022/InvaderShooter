
import java.awt.*;
import java.awt.geom.*;

public class Bullet extends BaseObject {
	// 弾幕タイプ
	private int bullettype;
	public void setBullettype(int bt) { bullettype = bt;}
	public int getBullettype() { return bullettype; }

	// 見た目のタイプ
	private int appearancetype;
	public void setAppearancetype(int at) { appearancetype = at;}
	public int getAppearancetype() { return appearancetype;}

	public Bullet()
	{
		super();
	}

	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;

		g2.setPaint(Color.orange);
		g2.fill(new Ellipse2D.Double(fX - 5f, fY - 5f, 10f, 10f));
	}

	//バレットタイプ
	public void Show2(Graphics2D g2)
	{
		if(!isEnable) return;

		g2.setPaint(Color.green);
		g2.fill(new Ellipse2D.Double(fX - 5f, fY - 5f, 10f, 10f));
	}

	public void Show3(Graphics2D g2)
	{
		if(!isEnable) return;

		g2.setPaint(Color.red);
		g2.fill(new Ellipse2D.Double(fX - 5f, fY - 5f, 8f, 8f));
	}

	public void Show4(Graphics2D g2)
	{
		if(!isEnable) return;

		g2.setPaint(Color.white);
		g2.fill(new Rectangle2D.Double(fX - 5f, fY - 5f, 10f, 500f));
	}
}

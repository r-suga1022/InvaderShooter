import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;

class Item extends BaseObject {
    private int type; // アイテムのタイプ
    public void setType(int t) { type = t;}
    public int getType() { return type;}

    // 全部で何種類あるか
    private int type_variation = 3;
    public int getTypeVariation() {return type_variation;}

    // 0:パワーアップ, 1:残機アップ, 2:毒（残機、パワー減少）
    private int poweruptype = 0;
    public int getPowerUpType() {return poweruptype;}
    private int leftuptype = 1;
    public int getLeftUpType() {return leftuptype;}
    private int powerdowntype = 2;
    public int getPowerDownType() {return powerdowntype;}

    private Image img;

    public Item() {
        super();
    }

    // アイテムを描画する
    public void Show(Graphics2D g2) {
        if(!isEnable) return;

        switch(getType()) {
            case 0:
                img = Toolkit.getDefaultToolkit().getImage("powerup.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
                break;
            case 1:
                img = Toolkit.getDefaultToolkit().getImage("leftup.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
                break;
            case 2:
                img = Toolkit.getDefaultToolkit().getImage("powerdown.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
                break;
            default:
                break;
        }
    }

}
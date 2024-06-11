import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;

class Item extends BaseObject {
    private int type; // �A�C�e���̃^�C�v
    public void setType(int t) { type = t;}
    public int getType() { return type;}

    // �S���ŉ���ނ��邩
    private int type_variation = 3;
    public int getTypeVariation() {return type_variation;}

    // 0:�p���[�A�b�v, 1:�c�@�A�b�v, 2:�Łi�c�@�A�p���[�����j
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

    // �A�C�e����`�悷��
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
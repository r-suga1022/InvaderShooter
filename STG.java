import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class STG extends JPanel implements Runnable, KeyListener{

	public static Thread mainThread = null;
	// ���C���֐�
	public static void main(String args[])
	{
		// �K����J�t���[����p��
		JFrame frame = new JFrame();

		// ���C���p�l���i�V���[�e�B���O�����s����p�l���j��V�K�쐬
		STG app = new STG();
		// �t���[���ɓo�^
		frame.getContentPane().add(app);
		// �e��t���[���̐ݒ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �E�B���h�E�X�^�C��
		//�T�C�Y�ύX
		frame.setBounds(10, 10, 680, 640); // �E�B���h�E�T�C�Y
		frame.setTitle("INVADER SHOOTER"); // �^�C�g��
		frame.setVisible(true); //�\��

		// ���C���X���b�h��
		mainThread = new Thread(app);
		
		// �ݒ�I������̂Ń��C���p�l�����������ĊJ�n
		app.init();
	}
	
	// �Q�[���}�l�[�W���̕ێ�(�ʃv���Q��)
	private GameManager _gmanager;

	// �`��Ώۃo�b�t�@
	private Image buffer;
	private Graphics bufferGraphics;
	
	public void init(){
		setBackground(Color.black);
		setForeground(Color.white);

		if (buffer == null){
			//�T�C�Y�ύX
			buffer = createImage(680, 640);
			bufferGraphics = buffer.getGraphics();
		}

		addKeyListener(this);
		requestFocus();
		
		_gmanager = new GameManager(this);
		
	    mainThread.start();
	}


	public void run(){
		while (true){
			try{
				Thread.sleep(20);	// FPS����
			}catch (InterruptedException e){
				break;
			}

			Graphics2D g2 = (Graphics2D) bufferGraphics;	// 2D�g������

			g2.setBackground(Color.black);
			//�T�C�Y�ύX
			g2.clearRect(0, 0, 680, 640);

			// �A���`�G�C���A�V���O
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			// �Q�[��������
			_gmanager.GameMainUpdate();	
			
			// ���̕ӂŕ`��
			ShowObjects(g2);
			
			//���y�C���g
			repaint();
		}
	}

	// �`�施��
	public void ShowObjects(Graphics2D g2)
	{
		_gmanager.State().Show(g2);
	}
	
	// �ĕ`�施�߂̍ۂɂ͂���𒣂�Ȃ���
	public void paintComponent(Graphics g){
			g.setColor(Color.black);
			//�T�C�Y�ύX
			g.clearRect(0, 0, 680, 640);
			g.drawImage(buffer, 0, 0, this);
	}

	// ���͌n�B��Ԃɂ��؂�ւ���
	public void keyPressed(KeyEvent e){
		_gmanager.State().KeyPressed(e);
	}

	public void keyReleased(KeyEvent e){
		_gmanager.State().KeyReleased(e);
	}
	public void keyTyped(KeyEvent e)
	{
		_gmanager.State().KeyTyped(e);
	}

}

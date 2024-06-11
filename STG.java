import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class STG extends JPanel implements Runnable, KeyListener{

	public static Thread mainThread = null;
	// メイン関数
	public static void main(String args[])
	{
		// 適当なJフレームを用意
		JFrame frame = new JFrame();

		// メインパネル（シューティングを実行するパネル）を新規作成
		STG app = new STG();
		// フレームに登録
		frame.getContentPane().add(app);
		// 各種フレームの設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウスタイル
		//サイズ変更
		frame.setBounds(10, 10, 680, 640); // ウィンドウサイズ
		frame.setTitle("INVADER SHOOTER"); // タイトル
		frame.setVisible(true); //表示

		// メインスレッド化
		mainThread = new Thread(app);
		
		// 設定終わったのでメインパネル初期化して開始
		app.init();
	}
	
	// ゲームマネージャの保持(別プロ参照)
	private GameManager _gmanager;

	// 描画対象バッファ
	private Image buffer;
	private Graphics bufferGraphics;
	
	public void init(){
		setBackground(Color.black);
		setForeground(Color.white);

		if (buffer == null){
			//サイズ変更
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
				Thread.sleep(20);	// FPS調整
			}catch (InterruptedException e){
				break;
			}

			Graphics2D g2 = (Graphics2D) bufferGraphics;	// 2D使うため

			g2.setBackground(Color.black);
			//サイズ変更
			g2.clearRect(0, 0, 680, 640);

			// アンチエイリアシング
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			// ゲーム内処理
			_gmanager.GameMainUpdate();	
			
			// この辺で描画
			ShowObjects(g2);
			
			//リペイント
			repaint();
		}
	}

	// 描画命令
	public void ShowObjects(Graphics2D g2)
	{
		_gmanager.State().Show(g2);
	}
	
	// 再描画命令の際にはこれを張りなおす
	public void paintComponent(Graphics g){
			g.setColor(Color.black);
			//サイズ変更
			g.clearRect(0, 0, 680, 640);
			g.drawImage(buffer, 0, 0, this);
	}

	// 入力系。状態により切り替える
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

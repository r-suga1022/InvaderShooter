import java.io.*;
import java.util.*;
import java.security.AccessControlException;
@SuppressWarnings("removal")
public class StageAnalyze {
	private Enemy[] _enemy = new Enemy[500];
	public Enemy[] GetTemporaryEnemy(){return _enemy;}
	private String[] _str = new String[500];		// 敵の数保存
	private String[] _token = new String[12];		// 分割文字保存
	private int _numStr = 0;		// 行数保存
	public int GetStringNumber(){return _numStr;}

	private LinkedList<String[]> _tokenList;
	public LinkedList<String[]> GetScenario(){return _tokenList;}
	
	// ステージデータを解析
	public void Analyze(String filePath)  throws AccessControlException
	{
		String temp = null;
		StringTokenizer st;
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader buffererdReader = null;

		try
		{
			inputStreamReader=new InputStreamReader(new FileInputStream(filePath));
			buffererdReader = new BufferedReader(inputStreamReader);

			_tokenList = new LinkedList<String[]>();
			
			//一行ずつ読む
			while (buffererdReader.ready()){
				// コメントは飛ばす
				if((temp = buffererdReader.readLine()).startsWith("//")) continue;
				// ボスの処理はここで解析
				else if(temp.startsWith("*"))
				{
					st = new StringTokenizer(temp, ",");
					for(int i=0; st.hasMoreTokens(); i++) {
						_token[i] = st.nextToken();
					}
					_tokenList.add(_token);
					continue;
				}

				// ここから通常敵
				_str[_numStr] = temp;
				// 分割する
				st = new StringTokenizer(_str[_numStr],",");
			
				_token = new String[12];
				// 分割したトークンを一時的に格納
				for(int i=0; st.hasMoreTokens(); i++) {
						_token[i] = st.nextToken();
				}
				
				if(_token[0] != null)
				{
					_tokenList.add(_token);
					_numStr++;
				}
				// デバッグ用に読み込んだステップ数を表示
				
				System.out.print("Token:");
				for(String[] s: _tokenList)
				{
					for(int i=0; i<s.length;i++)
					{
						System.out.print(s[i]+",");
					}
					System.out.println();
				}
				System.out.println();
			}
		}catch ( IOException e ){
			return;
		}finally{
			try{
				if(inputStreamReader!=null){
					inputStreamReader.close();
				}
				if(fileInputStream!=null){
					fileInputStream.close();
				}
				if(buffererdReader!=null){
					buffererdReader.close();
				}
			}catch ( IOException e ){
				return;
			}
		}
	}
	
	public Enemy GetTemporaryEnemy(EnemyManager em, int num)
	{
		String[] tempString = _tokenList.get(num);
		Enemy temp = new Enemy(em);
		temp.SetAppearTime(new Integer(tempString[0]).intValue());
		temp.SetPos((new Float(tempString[1]).floatValue()),(new Float(tempString[2]).floatValue()));
		temp.SetVX(new Float(tempString[3]).floatValue());
		temp.SetVY(new Float(tempString[4]).floatValue());
		temp.SetHP(new Integer(tempString[5]).intValue());
		temp.SetDEF(new Integer(tempString[6]).intValue());
		temp.SetBulletIntvl(new Integer(tempString[7]).intValue());
		temp.SetBulletType(new Integer(tempString[8]).intValue());
		temp.SetBulletSpeed(new Integer(tempString[9]).intValue());
		temp.SetEnemyType(new Integer(tempString[10]).intValue());//敵の見た目タイプ変更
		temp.SetAppearanceType(new Integer(tempString[11]).intValue()); //弾の見た目タイプ変更
	
		return temp;
	}
}

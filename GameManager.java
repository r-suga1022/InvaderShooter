
public class GameManager{
	// �Q�[���S�̂ŕK�v�ȕϐ��Ƃ��͂����ɕێ�����Ƃ����ł��傤
	// ��F�N�����Ă���̎��ԂȂ�
	
	// ���[�h�ؑ֗p
	private ModeState _modeState = null;
	public ModeState State(){return _modeState;}

	public GameManager(STG stg)
	{
		init();
	}
	
	// �������p
	public void init()
	{
		// �ŏ��̓^�C�g���֑J�ځA�ʃv���Q��
		_modeState = new TitleState();
	}
	
	// ���[�h�ύX�p
	public void ChangeMode(ModeState mode)
	{
		_modeState = mode;//�ʃv���Q��
	}

	// ���C��������
	public void GameMainUpdate()
	{
		_modeState.run(this);
	}
}

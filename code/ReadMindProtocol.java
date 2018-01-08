package ReadMindPackage;

public final class ReadMindProtocol {
	public static final String LOGIN = "100";
		//====== ex) 100:id:pw
	public static final String NEW_MEMBER = "101";
		//====== ex) 101:name:id:pw:tel
	public static final String ID_CHECK = "102";
		//====== ex) 102:id
	
	public static final String EXIT = "200";
		//====== ex) 200:id
	public static final String EXIT_GAME = "201";
	
	public static final String EXIT_GAMEA = "202";
	
	public static final String CHATTING = "300";
		//====== ex) 300:id:방번호:메세지       =================클라이언트가 서버로
		//====== ex) 300:id:메세지                  =================서버가 클라이언트로
	
	public static final String USERLIST = "400";
		//====== ex) 400
	public static final String ROOMLIST = "401";
		//====== ex) 401
	public static final String MAKEROOM = "402";
		//====== ex) 402:방제목
	public static final String INTOROOM = "403";
		//====== ex) 403
	public static final String INTOGETROOMLIST = "404";
		//====== ex) 404:방번호:인원수
	public static final String USERINFO = "405";
	
	
	
	public static final String GAMESTART = "500";
		//====== ex) 500
	public static final String GAMEIN = "501";
		//====== ex) 501:clientinfo
	public static final String GAMEROOMUSER = "502";
	
	public static final String GAMEENTER = "503";
	
	public static final String GAMEEND = "504";
	
	public static final String SETCOLOR = "505";
	
	
	public static final String DRAG = "600";
	public static final String PRESS = "601";
	public static final String RELEASE = "602";
	public static final String RESET = "603";
	
}
package DrawVenn;

public class TrainGraph extends Graph{
	public TrainGraph(){

		this.add(new Edge<String>("渋谷", "代官山"));
		this.add(new Edge<String>("代官山", "中目黒"));
		this.add(new Edge<String>("中目黒", "祐天寺"));
		this.add(new Edge<String>("祐天寺", "学芸大学"));
		this.add(new Edge<String>("学芸大学", "都立大学"));
		this.add(new Edge<String>("都立大学", "自由が丘"));
		this.add(new Edge<String>("自由が丘", "田園調布"));
		this.add(new Edge<String>("田園調布", "多摩川"));
		this.add(new Edge<String>("多摩川", "新丸子"));
		this.add(new Edge<String>("新丸子", "武蔵小杉"));
		this.add(new Edge<String>("武蔵小杉", "元住吉"));
		this.add(new Edge<String>("元住吉", "日吉"));
		this.add(new Edge<String>("日吉", "綱島"));
		this.add(new Edge<String>("綱島", "大倉山"));
		this.add(new Edge<String>("大倉山", "菊名"));
		this.add(new Edge<String>("菊名", "妙蓮寺"));
		this.add(new Edge<String>("妙蓮寺", "白楽"));
		this.add(new Edge<String>("白楽", "東白楽"));
		this.add(new Edge<String>("東白楽", "反町"));
		this.add(new Edge<String>("反町", "横浜"));
		
		this.add(new Edge<String>("渋谷", "池尻大橋"));
		this.add(new Edge<String>("池尻大橋", "三軒茶屋"));
		this.add(new Edge<String>("三軒茶屋", "駒沢大学"));
		this.add(new Edge<String>("駒沢大学", "桜新町"));
		this.add(new Edge<String>("桜新町", "用賀"));
		this.add(new Edge<String>("用賀", "二子玉川"));
		this.add(new Edge<String>("二子玉川", "二子新地"));
		this.add(new Edge<String>("二子新地", "高津"));
		this.add(new Edge<String>("高津", "溝の口"));
		this.add(new Edge<String>("溝の口", "梶が谷"));
		this.add(new Edge<String>("梶が谷", "宮崎台"));
		this.add(new Edge<String>("宮崎台", "宮前平"));
		this.add(new Edge<String>("宮前平", "鷺沼"));
		this.add(new Edge<String>("鷺沼", "たまプラーザ"));
		this.add(new Edge<String>("たまプラーザ", "あざみ野"));
		this.add(new Edge<String>("あざみ野", "江田"));
		this.add(new Edge<String>("江田", "市が尾"));
		this.add(new Edge<String>("市が尾", "藤が丘"));
		this.add(new Edge<String>("藤が丘", "青葉台"));
		this.add(new Edge<String>("青葉台", "田奈"));
		this.add(new Edge<String>("田奈", "長津田"));
		this.add(new Edge<String>("長津田", "つくし野"));
		this.add(new Edge<String>("つくし野", "すずかけ台"));
		this.add(new Edge<String>("すずかけ台", "南町田"));
		this.add(new Edge<String>("南町田", "つきみ野"));
		this.add(new Edge<String>("つきみ野", "中央林間"));
		
		this.add(new Edge<String>("長津田", "恩田"));
		this.add(new Edge<String>("恩田", "こどもの国"));
		
		this.add(new Edge<String>("大井町", "下神明"));
		this.add(new Edge<String>("下神明", "戸越公園"));
		this.add(new Edge<String>("戸越公園", "中延"));
		this.add(new Edge<String>("中延", "荏原町"));
		this.add(new Edge<String>("荏原町", "旗の台"));
		this.add(new Edge<String>("旗の台", "北千束"));
		this.add(new Edge<String>("北千束", "大岡山"));
		this.add(new Edge<String>("大岡山", "緑が丘"));
		this.add(new Edge<String>("緑が丘", "自由が丘"));
		this.add(new Edge<String>("自由が丘", "九品仏"));
		this.add(new Edge<String>("九品仏", "尾山台"));
		this.add(new Edge<String>("尾山台", "等々力"));
		this.add(new Edge<String>("等々力", "上野毛"));
		this.add(new Edge<String>("上野毛", "二子玉川"));
		this.add(new Edge<String>("二子玉川", "溝の口"));		
		
		this.add(new Edge<String>("三軒茶屋", "西太子堂"));
		this.add(new Edge<String>("西太子堂", "若林"));
		this.add(new Edge<String>("若林", "松陰神社前"));
		this.add(new Edge<String>("松陰神社前", "世田谷"));
		this.add(new Edge<String>("世田谷", "上町"));
		this.add(new Edge<String>("上町", "宮の坂"));
		this.add(new Edge<String>("宮の坂", "山下"));
		this.add(new Edge<String>("山下", "松原"));
		this.add(new Edge<String>("松原", "下高井戸"));

		this.add(new Edge<String>("目黒", "不動前"));
		this.add(new Edge<String>("不動前", "武蔵小山"));
		this.add(new Edge<String>("武蔵小山", "西小山"));
		this.add(new Edge<String>("西小山", "洗足"));
		this.add(new Edge<String>("洗足", "大岡山"));
		this.add(new Edge<String>("大岡山", "奥沢"));
		this.add(new Edge<String>("奥沢", "田園調布"));
		this.add(new Edge<String>("田園調布", "多摩川"));
		this.add(new Edge<String>("多摩川", "新丸子"));
		this.add(new Edge<String>("新丸子", "武蔵小杉"));
		this.add(new Edge<String>("武蔵小杉", "元住吉"));
		this.add(new Edge<String>("元住吉", "日吉"));

		this.add(new Edge<String>("多摩川", "沼部"));
		this.add(new Edge<String>("沼部", "鵜の木"));
		this.add(new Edge<String>("鵜の木", "下丸子"));
		this.add(new Edge<String>("下丸子", "武蔵新田"));
		this.add(new Edge<String>("武蔵新田", "矢口渡"));
		this.add(new Edge<String>("矢口渡", "蒲田"));
		
		this.add(new Edge<String>("五反田", "大崎広小路"));
		this.add(new Edge<String>("大崎広小路", "戸越銀座"));
		this.add(new Edge<String>("戸越銀座", "荏原中延"));
		this.add(new Edge<String>("荏原中延", "旗の台"));
		this.add(new Edge<String>("旗の台", "長原"));
		this.add(new Edge<String>("長原", "洗足池"));
		this.add(new Edge<String>("洗足池", "石川台"));
		this.add(new Edge<String>("石川台", "雪が谷大塚"));
		this.add(new Edge<String>("雪が谷大塚", "御嶽山"));
		this.add(new Edge<String>("御嶽山", "久が原"));
		this.add(new Edge<String>("久が原", "千鳥町"));
		this.add(new Edge<String>("千鳥町", "池上"));
		this.add(new Edge<String>("池上", "蓮沼"));
		this.add(new Edge<String>("蓮沼", "蒲田"));
	}	
	
}


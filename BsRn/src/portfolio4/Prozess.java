package portfolio4;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016 
 * @version 5.0
 */
public class Prozess {
	
	private int ankuftsZeit,laufZeit,tempLauf;
	private int warteZeit=0;
	private String id;											//name
    private static int counter = 1;
    public final int objectId;									//primary key
	private boolean[]marks;
	private boolean check=true;									//not needed
	//
	public Prozess(int aZeit,int lZeit,String id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.ankuftsZeit=aZeit;
		this.laufZeit=lZeit;
		this.tempLauf=lZeit;
		objectId=counter++;
	}
	//Getter and Setter
	public int getTempLauf() {
		return tempLauf;
	}

	public void setTempLauf(int tempLauf) {
		this.tempLauf = tempLauf;
	}

	public int getWarteZeit(){
		return warteZeit;
	}
	public void setWarteZeit(int warteZeit) {
		this.warteZeit = warteZeit;
	}
	public int getAnkuftsZeit() {
		return ankuftsZeit;
	}
	public void setAnkuftsZeit(int ankuftsZeit) {
		this.ankuftsZeit = ankuftsZeit;
	}
	public int getLaufZeit() {
		return laufZeit;
	}
	public void setLaufZeit(int laufZeit) {
		this.laufZeit = laufZeit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean[] getMarks() {
		return marks;
	}
	public void setMarks(boolean[] marks) {
		this.marks = marks;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public int getObjectId() {
		return objectId;
	}
	
}
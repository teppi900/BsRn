/**
 * 
 */
package portfolio4;

/**
 * @author kid
 *
 */
public class Prozess {
	
	private int ankuftsZeit,laufZeit;
	private int warteZeit=0;
	private String id;
    private static int counter = 1;
    public final int objectId;
	private boolean[]marks;
	private boolean check=true;
	
	public Prozess(int aZeit,int lZeit,String id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.ankuftsZeit=aZeit;
		this.laufZeit=lZeit;
		objectId=counter++;
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

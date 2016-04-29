/**
 * 
 */
package portfolio4;

/**
 * @author kid
 *
 */
public class Prozess {
	
	private int ankuftsZeit,laufZeit,warteZeit;
	private String id;
	
	public Prozess(int aZeit,int lZeit,String id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.ankuftsZeit=aZeit;
		this.laufZeit=lZeit;
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
}

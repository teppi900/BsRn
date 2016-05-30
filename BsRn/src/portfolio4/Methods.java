/**
 * 
 */
package portfolio4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author kid
 *
 */
public class Methods {
	private Scanner sc=new Scanner(System.in);
	private Scheduling s1=new Scheduling();
	private ArrayList<Prozess>prozessList;
	public void start(){
		try {
			System.out.println("==========================================");
			System.out.println("=================Simulator================");
			System.out.println("==========================================");
			System.out.println("Wv Prozesse?");
			int temp3=readzahlen3();
			System.out.println("==========================================");
			System.out.println("Prozesse herstellen:");
			System.out.println("0=Prozesse selbstherstellen");
			System.out.println("1=Demo/Random");
			int temp2=readZahlen2();
			System.out.println("==========================================");
			System.out.println("Schedulingsverfahren auswählen:");
			System.out.println("0=alle");
			System.out.println("1=SJF");
			System.out.println("2=FCFS");
			System.out.println("3=SRTF");
			System.out.println("4=LRTF");
			int temp=readZahlen();	
			System.out.println("==========================================");
			
			switch (temp) {
			case 0:
				ArrayList<Prozess>tempList=new ArrayList<>(prozessSpeicherung(temp2, temp3));
				s1.SJF(tempList);
				s1.FCFS(tempList);
				s1.SRTF(tempList);
				s1.LRTF(tempList);
				ask();
				break;
			case 1:
				s1.SJF(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			case 2:
				s1.FCFS(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			case 3:
				s1.SRTF(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			case 4:
				s1.LRTF(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			default:
				System.out.println("Fehler");
				ask();
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	public int readZahlen(){
		int temp=5;
		do {
			try {
				System.out.println("Zahl zwischen 0-4");
				temp=sc.nextInt();
			} catch (Exception e) {
				System.out.println("Nur Zahlen");
				readZahlen();
			}
		} while (temp>4||temp<0);
		
		return temp;
	}
	public int readZahlen2(){
		int temp2=3;
		do {
			try {
				System.out.println("Zahl zwischen 0-1");
				temp2=sc.nextInt();
			} catch (Exception e) {
				System.out.println("Nur Zahlen");
				readZahlen2();
			}
		} while (temp2>1||temp2<0);
		return temp2;
	}
	public int readzahlen3(){
		  int temp3=0;
		    do {
		try{
		     System.out.println("Zahl zwischen 2-10");
		     temp3=sc.nextInt();}
		catch(Exception e){
		System.out.println("Nur Zahlen");
		}
		    } while (temp3<2||temp3>10);
		  return temp3;
		 }
	
	public ArrayList<Prozess> prozessSpeicherung(int temp2,int temp3){
		if (temp2==0) {																				
			prozessList=new ArrayList<>(temp3);
			System.out.println(prozessList.size());
			for (int i = 0; i < temp3; i++) {
				System.out.println("Daten für Prozess "+i+": ");
				System.out.println("Name: ");
				String id=sc.next();
				int ankunftsZeit=21;
				do {
					try {
						System.out.println("Ankunftszeit: ");
						ankunftsZeit=sc.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Fehler");
							prozessSpeicherung(temp2, temp3);
					}
				} while (ankunftsZeit>20||ankunftsZeit<0);
				
				int laufZeit=0;
				do {
					try {
						System.out.println("Burst time: (1-10)");
						laufZeit=sc.nextInt();
					} catch (InputMismatchException e) {
						System.out.println("Fehler");
						prozessSpeicherung(temp2, temp3);
					}
					
				} while (laufZeit<1||laufZeit>10);
				
				System.out.println("===========================================================================");
				System.out.println("Prozess "+i+" id:"+id+", AnkunftsZeit:"+ankunftsZeit+", Burst time:"+laufZeit);
				Prozess tempProzess=new Prozess(ankunftsZeit,laufZeit,id);
				prozessList.add(i, tempProzess);
				
			}
		
		}
		else {
			prozessList=new ArrayList<>(temp3);
			for (int i = 0; i < temp3; i++) {
				Prozess p=new Prozess(randomZahl2(),randomZahl(),"r"+i);
				prozessList.add(i, p);
				System.out.println("Prozess "+i+".\n id: "+p.getId()+", Ankunftszeit: "+p.getAnkuftsZeit()+", Burst time: "+p.getLaufZeit());
			}	
			
		}
		prozessList.sort(Comparator.comparing(Prozess::getAnkuftsZeit));
		return prozessList;
	}
	public int randomZahl(){
		int random=(int)Math.round(Math.random()*5+1);
		return random;
	}
	public int randomZahl2(){
		int random=(int)Math.round(Math.random()*10);
		return random;
	}

	public ArrayList<Prozess> getProzessList() {
		return prozessList;
	}
	public void setProzessList(ArrayList<Prozess> prozessList) {
		this.prozessList = prozessList;
	}
	public void ask(){
		System.out.println("0: Remake \n1: End");
		int n=2;
		try {
			n=sc.nextInt();
		} catch (Exception e) {
			System.out.println("meine Fresse");
			ask();
		}
		
		if (n==0) {
			start();
		}
		else if(n==1){
			sc.close();
			System.out.println("Bye");
			System.exit(0);
		}
		else {
			System.out.println("Fehler "+n+" geht nicht");
			ask();
		}
	}
}

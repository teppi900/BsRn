/**
 * 
 */
package portfolio4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016 
 * @version 5.0
 */
public class Methods {
	private Scanner sc=new Scanner(System.in);
	private Scheduling s1=new Scheduling();
	private ArrayList<Prozess>prozessList;
	public void start(){
		try {
			//Ui
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
			
			//choosing Scheduling methods
			switch (temp) {
			//Case 0 
			case 0:	
				ArrayList<Prozess>tempList=new ArrayList<>(prozessSpeicherung(temp2, temp3));
				s1.SJF(tempList);
				s1.FCFS(tempList);
				s1.SRTF(tempList);
				s1.LRTF(tempList);
				ask();
				break;
			//Case 1 Shortest Job First
			case 1:
				s1.SJF(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			//Case 2 First Come First Served
			case 2:
				s1.FCFS(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			//Case 3 Shortest Remaining Time First
			case 3:
				s1.SRTF(prozessSpeicherung(temp2, temp3));
				ask();
				break;
			//Case 4 Longest Remaining Time First
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
	//User input: chooses Scheduling Method
	public int readZahlen(){
		int temp=5;
		do {
			try {
				System.out.println("Zahl zwischen 0-4");
				temp=sc.nextInt();									//Scans the next Number
			} catch (Exception e) {									//if user can't read or input something besides an integer --> Programm kills itself
				System.out.println("Fehler");
				System.out.println("bitte neu starten");
				System.exit(0);
			}
		} while (temp>4||temp<0);									//will call the do part until the user chooses a number between 0 and 4
		
		return temp;												
	}
	//User input: decision between Process generating methods
	public int readZahlen2(){
		int temp2=3;
		do {
			try {
				System.out.println("Zahl zwischen 0-1");
				temp2=sc.nextInt();									//Scans the next Number
			} catch (Exception e) {									//if user can't read or input something besides an integer --> Programm kills itself
				System.out.println("Fehler");
				System.out.println("bitte neu starten");
				System.exit(0);
			}
		} while (temp2>1||temp2<0);									//will call the do part until the user chooses a number between 0 and 1
		return temp2;
	}
	//User input: Amount of Processes
	public int readzahlen3(){
		  int temp3=0;
		    do {
		try{
		     System.out.println("Zahl zwischen 2-10");
		     temp3=sc.nextInt();}									//Scans the next Number
		catch(Exception e){											//if user can't read or input something besides an integer --> Programm kills itself
			System.out.println("Fehler");
			System.out.println("bitte neu starten");
			System.exit(0);
		}
		    } while (temp3<2||temp3>10);							//will call the do part until the user chooses a number between 2 and 10
		  return temp3;
		 }
	//Method to generate Processes and saving them inside a container
	//Takes the Input from readZahlen2 and readZahlen3
	public ArrayList<Prozess> prozessSpeicherung(int temp2,int temp3){	
		//Self input version
		if (temp2==0) {																								
			prozessList=new ArrayList<>(temp3);						
			System.out.println(prozessList.size());
			for (int i = 0; i < temp3; i++) {						
				System.out.println("Daten für Prozess "+i+": ");
				System.out.println("Name: ");								
				String id=sc.next();								//get the name of the process
				int ankunftsZeit=21;
				do {
					try {
						System.out.println("Ankunftszeit: (0-10) ");		
						ankunftsZeit=sc.nextInt();					//get the start time
					} catch (Exception e) {
						System.out.println("Fehler");
						System.out.println("bitte neu starten");
						System.exit(0);
					}
				} while (ankunftsZeit>10||ankunftsZeit<0);			//start has to be between 10 and 0
				
				int laufZeit=0;
				do {
					try {
						System.out.println("Burst time: (1-10)");
						laufZeit=sc.nextInt();						//get cpu burst time
					} catch (InputMismatchException e) {
						System.out.println("Fehler");
						System.out.println("bitte neu starten");
						System.exit(0);
					}
					
				} while (laufZeit<1||laufZeit>10);					//cpu burst time has to be between 1 and 10
				
				System.out.println("===========================================================================");
				System.out.println("Prozess "+i+" id:"+id+", AnkunftsZeit:"+ankunftsZeit+", Burst time:"+laufZeit);
				//saving informations as a process and into a container
				Prozess tempProzess=new Prozess(ankunftsZeit,laufZeit,id);
				prozessList.add(i, tempProzess);					
				
			}
		
		}
		//random generated process version
		else {
			prozessList=new ArrayList<>(temp3);
			//for the size of prozessList a random generated Prozess will be made and added into the container
			for (int i = 0; i < temp3; i++) {
				Prozess p=new Prozess(randomZahl2(),randomZahl(),"r"+i);		//process p uses the methods randomZahl2 and randomZahl1 to generate their start and cpu busrt time
				prozessList.add(i, p);											//add process p to container
				System.out.println("Prozess "+i+".\n id: "+p.getId()+", Ankunftszeit: "+p.getAnkuftsZeit()+", Burst time: "+p.getLaufZeit());
			}	
			
		}
		//Container sorted by ankunftsZeit
		prozessList.sort(Comparator.comparing(Prozess::getAnkuftsZeit));
		return prozessList;
	}
	public int randomZahl(){
		int random=(int)Math.round(Math.random()*5+1); //random number between 1-6
		return random;
	}
	public int randomZahl2(){
		int random=(int)Math.round(Math.random()*10); //random number between 0-10
		return random;
	}

	public ArrayList<Prozess> getProzessList() {
		return prozessList;
	}
	public void setProzessList(ArrayList<Prozess> prozessList) {
		this.prozessList = prozessList;
	}
	//User input: stop or again
	public void ask(){	
		System.out.println("0: Remake \t1: End");
		int n=2;
		try {
			n=sc.nextInt();
		} catch (Exception e) {
			System.out.println("meine Fresse");
			System.exit(0);
		}
		//0 = again
		if (n==0) {
			start();
		}
		//anything else = close
		else {
			sc.close();
			System.out.println("Bye");
			System.exit(0);
		}
		
	}
}
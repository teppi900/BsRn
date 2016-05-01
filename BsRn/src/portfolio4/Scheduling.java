/**
 * 
 */
package portfolio4;

import java.util.ArrayList;

/**
 * @author kid
 *
 */
public class Scheduling {
	private Methods m;

	public void SJF(){
		m.getProzessList();
		
	}
	public void FCFS(ArrayList<Prozess>sizes){									 			// Problem beim Prozessen die die selben Ankunftszeit haben
		int[]finishedTimes=new int[sizes.size()];
		int[]ankunftsZeit=new int[sizes.size()];
		for (int i = 0; i < ankunftsZeit.length; i++) {
			ankunftsZeit[i]=sizes.get(i).getAnkuftsZeit();
		}
		int[]laufZeit=new int[sizes.size()];
		for (int i = 0; i < laufZeit.length; i++) {
			laufZeit[i]=sizes.get(i).getLaufZeit();
		}
		finishedTimes[0] = ankunftsZeit[0] + laufZeit[0];
		System.out.println(finishedTimes[0]);
		for (int k = 1; k < sizes.size(); k++) {
			if (ankunftsZeit[k]<=finishedTimes[k-1]) {
				finishedTimes[k] = finishedTimes[k - 1] + laufZeit[k];
			}else {
				finishedTimes[k]=ankunftsZeit[k]+laufZeit[k];
			}
		}	
		int length=finishedTimes[finishedTimes.length-1];
		for (int i = 0; i <length ; i++) {
			if (i<10) {
				System.out.print("\t"+"0"+i);
			}else {
				System.out.print("\t"+i);
			}
		}
		int temp=ankunftsZeit[0];
		int freeTime=0;	
		for (int i = 0; i < sizes.size(); i++) {
			System.out.print("\n"+sizes.get(i).getId()+"\t");
			if (ankunftsZeit[i]>temp) {															//Lücken zwischen Prozessen
				freeTime=ankunftsZeit[i]-temp;
				temp=temp+freeTime;
				tempLength(temp);

			}
			else if (ankunftsZeit[i]<=temp) {
				int tempSave=ankunftsZeit[i];
				ankunftsZeit[i]=temp;
				tempLength(ankunftsZeit[i]);
				ankunftsZeit[i]=tempSave;
				int waitZeit=temp-ankunftsZeit[i];
				sizes.get(i).setWarteZeit(waitZeit);
			}
				for (int j = 0; j < laufZeit[i]; j++) {											//Laufzeit
					System.out.print("*"+"\t");																
				}																				//wenn fertig Laufzeit zum temp addieren
					temp+=laufZeit[i];
		}
		System.out.println();
		for (int i = 0; i < sizes.size(); i++) {												//Ausgabe von Daten
			System.out.println("Prozess id: "+sizes.get(i).getId()+", Ankunftszeit: "+sizes.get(i).getAnkuftsZeit()+", Laufzeit: "+sizes.get(i).getLaufZeit()+", Waittime: "+sizes.get(i).getWarteZeit());
		}
		System.out.println("Average Waittime: " +averageWait(sizes));
		System.out.println("Average Bursttime: "+averageLauf(sizes));
	}
	public void SRTF(){
		m.getProzessList();
	}
	public void LRTF(){
		m.getProzessList();
	}
	public double averageWait(ArrayList<Prozess>prozess){
		double avgWait=0;
		for (int i = 0; i < prozess.size(); i++) {
			avgWait=avgWait+prozess.get(i).getWarteZeit();
		}
		avgWait/=prozess.size();
		avgWait=(int)avgWait + (Math.round(Math.pow(10,2)*(avgWait-(int)avgWait)))/(Math.pow(10,2));
		return avgWait; 
	}
	public double averageLauf(ArrayList<Prozess>prozess){
		double avgLauf=0;
		for (int i = 0; i < prozess.size(); i++) {
			avgLauf=avgLauf+prozess.get(i).getLaufZeit();
		}
		avgLauf/=prozess.size();
		avgLauf=(int)avgLauf + (Math.round(Math.pow(10,2)*(avgLauf-(int)avgLauf)))/(Math.pow(10,2));
		return avgLauf;
	}
	public double tempLength(int ankunftsTime){												
		double tempLength=ankunftsTime;
		for (int k = 0; k < tempLength; k++) {
			System.out.print(" "+"\t");
		}
		return tempLength;
	}
}

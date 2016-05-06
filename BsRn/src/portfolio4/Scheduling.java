/**
 * 
 */
package portfolio4;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author kid3
 *
 */
public class Scheduling {
	private Methods m;
	
	public void SJF(ArrayList<Prozess>sizes){
		ArrayList<Prozess>tempProzess=new ArrayList<>(sizes);
		int tempCounter=1;
		while (tempProzess.get(0).getAnkuftsZeit()==tempProzess.get(tempCounter).getAnkuftsZeit()) {
			tempCounter++;
			if (tempCounter==tempProzess.size()) {
				break;
			}
			
			
		}
		if (tempCounter>1) {
			ArrayList<Prozess>rePosition=new ArrayList<>();
			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));
			}
			System.out.println(rePosition.size());
			rePosition.sort(Comparator.comparing(Prozess::getLaufZeit));
			for (int i = 0; i < rePosition.size(); i++) {
				System.out.println(rePosition.get(i).getLaufZeit());
				tempProzess.set(i, rePosition.get(i));
			}
		}
			// bug with last item
		
		int[]finishedTimes=new int[tempProzess.size()];
	
		finishedTimes[0] = tempProzess.get(0).getAnkuftsZeit() + tempProzess.get(0).getLaufZeit();
		for (int j = 1; j < tempProzess.size(); j++) {
			int temp=0;
			while (finishedTimes[j-1]>=tempProzess.get(j+temp).getAnkuftsZeit()) {
				temp++;
				if (temp+j==tempProzess.size()) {
					break;
				}
			}
			if (temp!=0) {
				ArrayList<Prozess>tempTemp=new ArrayList<>(temp);
				for (int i = 0; i < temp; i++) {
					tempTemp.add(i, tempProzess.get(i+j));
				}
				tempTemp.sort(Comparator.comparing(Prozess::getLaufZeit));
				for (int i = 0; i < temp; i++) {
					tempProzess.remove(i+j);
					tempProzess.add(i+j, tempTemp.get(i));
				}
				finishedTimes[j]=finishedTimes[j-1]+tempProzess.get(j).getLaufZeit();
			}else {
				finishedTimes[j]=tempProzess.get(j).getAnkuftsZeit()+tempProzess.get(j).getLaufZeit();
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
		int temp=tempProzess.get(0).getAnkuftsZeit();
		int freeTime=0;	
		for (int i = 0; i < tempProzess.size(); i++) {
			System.out.print("\n"+tempProzess.get(i).getId()+"\t");
			if (tempProzess.get(i).getAnkuftsZeit()>temp) {															//Lücken zwischen Prozessen
				freeTime=tempProzess.get(i).getAnkuftsZeit()-temp;
				temp=temp+freeTime;
				tempLength(temp);

			}
			else if (tempProzess.get(i).getAnkuftsZeit()<=temp) {
				int tempSave=tempProzess.get(i).getAnkuftsZeit();
				
				tempLength(temp);
				tempProzess.get(i).setAnkuftsZeit(tempSave);
				int waitZeit=temp-tempProzess.get(i).getAnkuftsZeit();
				tempProzess.get(i).setWarteZeit(waitZeit);
			}
				for (int j = 0; j < tempProzess.get(i).getLaufZeit(); j++) {										//Laufzeit
					System.out.print("*"+"\t");																
				}																									//wenn fertig Laufzeit zum temp addieren
					temp+=tempProzess.get(i).getLaufZeit();
		}
		System.out.print("\n\n");
		for (int i = 0; i < tempProzess.size(); i++) {																//Ausgabe von Daten
			System.out.println("Prozess id: "+tempProzess.get(i).getId()+", Ankunftszeit: "+tempProzess.get(i).getAnkuftsZeit()+", Laufzeit: "+tempProzess.get(i).getLaufZeit()+", Waittime: "+tempProzess.get(i).getWarteZeit());
		}
		System.out.println("Average Waittime: " +averageWait(tempProzess));
		System.out.println("Average Bursttime: "+averageLauf(tempProzess));
	}
		
	public void FCFS(ArrayList<Prozess>sizes){									 			
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
		System.out.print("\n\n");
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
	public void tempLength(int ankunftsTime){												
		int tempLength=ankunftsTime;
		for (int k = 0; k < tempLength; k++) {
			System.out.print(" "+"\t");
		}
	}
}

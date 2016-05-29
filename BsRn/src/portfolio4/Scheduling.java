/**
 * 
 */
package portfolio4;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author kid3
 *Wartezeit+Laufzeit = full laufzeit
 *cmd programm
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
			System.out.println("Prozess id: "+tempProzess.get(i).getId()+", Ankunftszeit: "+tempProzess.get(i).getAnkuftsZeit()+", Laufzeit: "+tempProzess.get(i).getLaufZeit()+", Waittime: "+tempProzess.get(i).getWarteZeit()+", BurstTime: "+(tempProzess.get(i).getLaufZeit()+tempProzess.get(i).getWarteZeit()));
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
			System.out.println("Prozess id: "+sizes.get(i).getId()+", Ankunftszeit: "+sizes.get(i).getAnkuftsZeit()+", Laufzeit: "+sizes.get(i).getLaufZeit()+", Waittime: "+sizes.get(i).getWarteZeit()+", BurstTime: "+(sizes.get(i).getLaufZeit()+sizes.get(i).getWarteZeit()));
		}
		System.out.println("Average Waittime: " +averageWait(sizes));
		System.out.println("Average Bursttime: "+averageLauf(sizes));
	}
	public void SRTF(ArrayList<Prozess>sizes){
		ArrayList<Prozess>tempProzess=new ArrayList<>(sizes);
		ArrayList<Integer>ankunftsZeit=new ArrayList<>();
		ArrayList<Integer>clone=new ArrayList<>();
		ArrayList<Prozess>buffer=new ArrayList<>();
		int tempCounter=1;
		
		for (int i = 0; i < tempProzess.size(); i++) {
			ankunftsZeit.add(i, tempProzess.get(i).getAnkuftsZeit());
		}
		for (int i = 0; i < ankunftsZeit.size()-1; i++) {												//ankunfsZeit aussortieren
			if (ankunftsZeit.get(i)==ankunftsZeit.get(i+1)) {
				ankunftsZeit.set(i, null);
			}
		}
		for (int i = 0; i < ankunftsZeit.size(); i++) {													//ankunftsZeit einmalig abspeichern
			if (ankunftsZeit.get(i)!=null) {
				clone.add(ankunftsZeit.get(i));
			}
		}
		
		int[]finishedTimes=new int[tempProzess.size()];
		finishedTimes[0] = tempProzess.get(0).getAnkuftsZeit() + tempProzess.get(0).getLaufZeit();
		for (int k = 1; k < sizes.size(); k++) {
			if (tempProzess.get(k).getAnkuftsZeit()<=finishedTimes[k-1]) {
				finishedTimes[k] = finishedTimes[k - 1] + tempProzess.get(k).getLaufZeit();
			}else {
				finishedTimes[k]=tempProzess.get(k).getAnkuftsZeit() + tempProzess.get(k).getLaufZeit();
			}
		}
		clone.add(finishedTimes[finishedTimes.length-1]);
		clone.remove(0);
		while (tempProzess.get(0).getAnkuftsZeit()==tempProzess.get(tempCounter).getAnkuftsZeit()) {	//sortieren nach laufzeit beim 0
			tempCounter++;
			if (tempCounter==tempProzess.size()) {
				break;
			}
		}
		for (int i = 0; i < tempProzess.size(); i++) {		// range marker
			for (int j = 0; j < finishedTimes[finishedTimes.length-1]; j++) {
				boolean [] mark=new boolean[finishedTimes[finishedTimes.length-1]];
				mark[j]=false;
				tempProzess.get(i).setMarks(mark);
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
				buffer.add(rePosition.get(i));
			}
		}
		else {
			buffer.add(tempProzess.get(0));
		}
		int start=buffer.get(0).getAnkuftsZeit();//!! KEIN Daten drin
	System.out.println(buffer.get(0).getMarks().length);
	//srtf
	for (int i = start; i < finishedTimes[finishedTimes.length-1]; i++) {
		if (i<clone .get(0)) {
			//i kleine als nachster knackpunkt
			if (buffer.get(0).getLaufZeit()>0) {
				buffer.get(0).setLaufZeit(buffer.get(0).getLaufZeit()-1);
				buffer.get(0).getMarks()[i]=true;
			}
			else if (buffer.get(0).getLaufZeit()==0) {
				buffer.get(0).setCheck(false);																					//brauch man bis jetzt nicht
				for (int j = 0; j < tempProzess.size(); j++) {
					if (tempProzess.get(j).getObjectId()==buffer.get(0).getObjectId()) {
						tempProzess.get(j).setMarks(buffer.get(0).getMarks());
						tempProzess.get(j).setCheck(buffer.get(0).isCheck());
						break;
					}
				}
				if (buffer.size()>1) {
					buffer.remove(0);
				}
				else if (buffer.size()==1&&clone.size()>1) {																//freeTime Problem
					for (int j = 0; j < tempProzess.size(); j++) {
						if (clone.size()>1) {
							if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {
								buffer.add(tempProzess.get(j));
							}
						}
					}
					buffer.remove(0);
					i=clone.get(0);
					buffer.sort(Comparator.comparing(Prozess::getLaufZeit));
				}
				i-=1;
			}
		}
		else if (i==clone.get(0)) {
			//i==nachster knackpunkt
			if (clone.size()>1) {
			for (int j = 0; j < tempProzess.size(); j++) {
				if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {
					buffer.add(tempProzess.get(j));
				}
			}
			buffer.sort(Comparator.comparing(Prozess::getLaufZeit));
			clone.remove(0);
			i-=1;
			}
		}
	}
	//----------------srtf-----------------------
	//----------------done-----------------------------
	//waittime
	for (int i = 0; i < tempProzess.size(); i++) {
		int count=0;
		int lauf=tempProzess.get(i).getLaufZeit();
		for (int j = tempProzess.get(i).getAnkuftsZeit(); j < tempProzess.get(i).getMarks().length; j++) {
			if (tempProzess.get(i).getMarks()[j]==true) {
				lauf--;
				if (lauf==0) {
					break;
				}
			}
			else if (tempProzess.get(i).getMarks()[j]==false) {
				count++;
			}
		}
		tempProzess.get(i).setWarteZeit(count);
	}
	//waittime
			int length=finishedTimes[finishedTimes.length-1];
			for (int i = 0; i <length ; i++) {
				if (i<10) {
					System.out.print("\t"+"0"+i);
				}else {
					
					System.out.print("\t"+i);
				}
			}
			for (int i = 0; i < tempProzess.size(); i++) {
				System.out.print("\n"+tempProzess.get(i).getId()+"\t");
				boolean[]tempMark=tempProzess.get(i).getMarks();
				for (int k = 0; k < tempMark.length; k++) {
					if (tempMark[k]==false) {
						System.out.print("\t");
					}else {
						System.out.print("*\t");
					}
				}
			}
			System.out.print("\n\n");
			for (int i = 0; i < tempProzess.size(); i++) {												//Ausgabe von Daten
				System.out.println("Prozess id: "+tempProzess.get(i).getId()+", Ankunftszeit: "+tempProzess.get(i).getAnkuftsZeit()+", Laufzeit: "+tempProzess.get(i).getLaufZeit()+", Waittime: "+tempProzess.get(i).getWarteZeit()+", BurstTime: "+(tempProzess.get(i).getLaufZeit()+tempProzess.get(i).getWarteZeit()));
			}
			System.out.println("Average Waittime: " +averageWait(tempProzess));
			System.out.println("Average Bursttime: "+averageLauf(tempProzess));
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
			avgLauf=avgLauf+prozess.get(i).getLaufZeit()+prozess.get(i).getWarteZeit();
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

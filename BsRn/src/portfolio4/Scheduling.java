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
	public void FCFS(ArrayList<Prozess>sizes){												// Problem beim Prozessen die die selben Ankunftszeit haben
		int[]waitingTimes=new int[sizes.size()];
		int[]finishedTimes=new int[sizes.size()];
		int[]ankunftsZeit=new int[sizes.size()];
		for (int i = 0; i < ankunftsZeit.length; i++) {
			ankunftsZeit[i]=sizes.get(i).getAnkuftsZeit();
//			System.out.println(ankunftsZeit[i]);
		}
//		System.out.println(ankunftsZeit.length);
		int[]laufZeit=new int[sizes.size()];
		for (int i = 0; i < laufZeit.length; i++) {
			laufZeit[i]=sizes.get(i).getLaufZeit();
//			System.out.println(laufZeit[i]);
		}
//		System.out.println(laufZeit.length);
		finishedTimes[0] = ankunftsZeit[0] + laufZeit[0];
		System.out.println(finishedTimes[0]);
		for (int k = 1; k < sizes.size(); k++) {
			if (ankunftsZeit[k]<=finishedTimes[k-1]) {
				finishedTimes[k] = finishedTimes[k - 1] + laufZeit[k];
//				System.out.print(finishedTimes[k]+"="+finishedTimes[k-1]+"+"+laufZeit[k]);
			}else {
				finishedTimes[k]=ankunftsZeit[k]+laufZeit[k];
//				System.out.print(finishedTimes[k]+"="+k+"+"+laufZeit[k]);
			}
//		     System.out.println(finishedTimes[k]);
		}	
//		System.out.println(finishedTimes.length);
//		int temp2=sizes.get(0).getAnkuftsZeit();//Länge der leerspace
//		int temp=0;
//		int ankunft=sizes.get(0).getAnkuftsZeit();									//gesamt lange
//		for (int i = 0; i < sizes.size(); i++) {
//			ankunft=ankunft+sizes.get(i).getLaufZeit();
//		}
		int length=finishedTimes[finishedTimes.length-1];
		for (int i = 0; i <length ; i++) {
			if (i<10) {
				System.out.print("\t"+"0"+i);
			}else {
				System.out.print("\t"+i);
			}
		}
//		for (int i = 0; i < ankunft; i++) {
//			System.out.print("  "+i);
//			if (i==ankunft-1) {
//				System.out.print("\n");
//			}
//		}
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
			}
				for (int j = 0; j < laufZeit[i]; j++) {											//Laufzeit
					System.out.print("*"+"\t");																
				}																			//wenn fertig Laufzeit zum temp addieren
				temp+=laufZeit[i];
		}
	}
	public void SRTF(){
		m.getProzessList();
	}
	public void LRTF(){
		m.getProzessList();
	}
	public void averageWait(){
		
	}
	public void averageLauf(){
		
	}
	public int tempLength(int ankunftsTime){												
		int tempLength=ankunftsTime;
		for (int k = 0; k < tempLength; k++) {
			System.out.print(" "+"\t");
		}
		return tempLength;
	}
}

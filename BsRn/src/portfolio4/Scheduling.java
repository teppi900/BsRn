package portfolio4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Zakaria EI Boujattoui, Linus Städtler and Anh Phuc Hoang
 * @since 01.06.2016 
 * @version 5.0
 */
public class Scheduling {
	//Shortest Remaining Time First
	public void SJF(ArrayList<Prozess>sizes){
		ArrayList<Prozess>tempProzess=new ArrayList<>(sizes);
		//check if the first numbers have the same ankunftsZeit
		int tempCounter=1;
		while (tempProzess.get(0).getAnkuftsZeit()==tempProzess.get(tempCounter).getAnkuftsZeit()) {
			tempCounter++;
			if (tempCounter==tempProzess.size()) {
				break;
			}
		}
		//if yes 
		if (tempCounter>1) {
			ArrayList<Prozess>rePosition=new ArrayList<>();
			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));			//temporary container for those numbers 
			}
			rePosition.sort(Comparator.comparing(Prozess::getLaufZeit));	//sort by LaufZeit
			for (int i = 0; i < rePosition.size(); i++) {		
				tempProzess.set(i, rePosition.get(i));			//re add them into the main List
			}
		}
		//calculation for finishedTimes
		int[]finishedTimes=new int[tempProzess.size()];	
		finishedTimes[0] = tempProzess.get(0).getAnkuftsZeit() + tempProzess.get(0).getLaufZeit(); //finishedTime for the first Process
		for (int j = 1; j < tempProzess.size(); j++) {
			int temp=0;
			while (finishedTimes[j-1]>=tempProzess.get(j+temp).getAnkuftsZeit()) {                 //check the finishedTime of the previous process with the ankunftsZeit of the j+temp
				temp++;																			   //if ankunftsZeit is smaller than that of the previous process we check the next process ankuftsZeit
				if (temp+j==tempProzess.size()) {
					break;																			//until the last process
				}
			}
			if (temp!=0) {																			//if there are processes which start before the previous one finished
				ArrayList<Prozess>tempTemp=new ArrayList<>(temp);
				for (int i = 0; i < temp; i++) {
					tempTemp.add(i, tempProzess.get(i+j));											//we add them into a temporary Container
				}
				tempTemp.sort(Comparator.comparing(Prozess::getLaufZeit));							//and sort them by LaufZeit
				for (int i = 0; i < temp; i++) {
					tempProzess.remove(i+j);
					tempProzess.add(i+j, tempTemp.get(i));											//re add them
				}																					
				//Calculation for the next finishedTime
				finishedTimes[j]=finishedTimes[j-1]+tempProzess.get(j).getLaufZeit();				
			}
			else {
				int tempC=1;
				//check if the next AnkunftsZeit are the same
				if (j<tempProzess.size()-1) {	
					while (tempProzess.get(j).getAnkuftsZeit()==tempProzess.get(j+tempC).getAnkuftsZeit()  ) {
						tempC++;
						if (j+tempC==tempProzess.size()) {
							break;
						}
					}
					//if yes 
					if (tempC>1) {
						ArrayList<Prozess>rePo=new ArrayList<>();
						for (int i = 0; i < tempC; i++) {
							rePo.add(i, tempProzess.get(j+i));					//temporary container for those numbers 
						}
						rePo.sort(Comparator.comparing(Prozess::getLaufZeit));	//sort by LaufZeit
						for (int i = j; i < j+rePo.size(); i++) {		
							tempProzess.set(i, rePo.get(i-j));					//re add them into the main List
						}
					
					
					}
				}
					//calculation for finishedTimes
					finishedTimes[j]=tempProzess.get(j).getAnkuftsZeit()+tempProzess.get(j).getLaufZeit();			
			}	
		}
		//Print 
		System.out.println();
		System.out.println("Shortest Job First");
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
			System.out.print("\n"+tempProzess.get(i).getId()+"\t");					//print name of the process												
			if (tempProzess.get(i).getAnkuftsZeit()>temp) {							//if the ankunftsZeit of the next process is later than temp							
				freeTime=tempProzess.get(i).getAnkuftsZeit()-temp;					//equals freetime
				temp=temp+freeTime;											
				tempLength(temp);													//calls tempLength
			}
			else if (tempProzess.get(i).getAnkuftsZeit()<=temp) {					//ankunftsZeit of the next process is earlier or equal temp					
				int tempSave=tempProzess.get(i).getAnkuftsZeit();					
				tempLength(temp);													
				tempProzess.get(i).setAnkuftsZeit(tempSave);
				int waitZeit=temp-tempProzess.get(i).getAnkuftsZeit();				
				tempProzess.get(i).setWarteZeit(waitZeit);
			}
				for (int j = 0; j < tempProzess.get(i).getLaufZeit(); j++) {		//print of burst time
					System.out.print("*"+"\t");																
				}																
					temp+=tempProzess.get(i).getLaufZeit();							//if done cpu burst time will be added to temp
		}
		//Print of Data
		printData(tempProzess,0);
	}
	//First Come First Served
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
		//calculation of finishedTimes
		finishedTimes[0] = ankunftsZeit[0] + laufZeit[0];
		for (int k = 1; k < sizes.size(); k++) {
			if (ankunftsZeit[k]<=finishedTimes[k-1]) {									
				finishedTimes[k] = finishedTimes[k - 1] + laufZeit[k];
			}else {																		
				finishedTimes[k]=ankunftsZeit[k]+laufZeit[k];							
			}
		}	
		//Print count of finishedTime
		System.out.println();
		System.out.println("First Come First Served");
		int length=finishedTimes[finishedTimes.length-1];
		for (int i = 0; i <length ; i++) {
			if (i<10) {
				System.out.print("\t"+"0"+i);
			}else {
				System.out.print("\t"+i);
			}
		}
		//Fcfs Print
		int temp=ankunftsZeit[0];
		int freeTime=0;	
		for (int i = 0; i < sizes.size(); i++) {	
			System.out.print("\n"+sizes.get(i).getId()+"\t");
			if (ankunftsZeit[i]>temp) {																										
				freeTime=ankunftsZeit[i]-temp;													
				temp=temp+freeTime;																//Space between processes+temp
				tempLength(temp);																
			}
			else if (ankunftsZeit[i]<=temp) {													//start time of the next one will be the same as temp
				int tempSave=ankunftsZeit[i];
				ankunftsZeit[i]=temp;
				tempLength(ankunftsZeit[i]);
				ankunftsZeit[i]=tempSave;
				int waitZeit=temp-ankunftsZeit[i];												//wait time
				sizes.get(i).setWarteZeit(waitZeit);
			}
				for (int j = 0; j < laufZeit[i]; j++) {											//Print
					System.out.print("*"+"\t");																
				}																				
					temp+=laufZeit[i];															//if done temp+LaufZeit
		}
		printData(sizes,1);
	}
	//Shortest remaining time first
	public void SRTF(ArrayList<Prozess>sizes){
		ArrayList<Prozess>tempProzess=new ArrayList<>(sizes);
		ArrayList<Integer>ankunftsZeit=new ArrayList<>();
		ArrayList<Integer>clone=new ArrayList<>();
		ArrayList<Prozess>buffer=new ArrayList<>();
		int tempCounter=1;
		//warm up session
		for (int i = 0; i < tempProzess.size(); i++) {
			ankunftsZeit.add(i, tempProzess.get(i).getAnkuftsZeit());
		}
		for (int i = 0; i < ankunftsZeit.size()-1; i++) {												//separate ankunfsZeit 
			if (ankunftsZeit.get(i)==ankunftsZeit.get(i+1)) {
				ankunftsZeit.set(i, null);
			}
		}
		for (int i = 0; i < ankunftsZeit.size(); i++) {													//save ankunftsZeit 
			if (ankunftsZeit.get(i)!=null) {
				clone.add(ankunftsZeit.get(i));
			}
		}
		//calculation of finishedTimes
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
		//get all the processes that have the same ankunftsZeit as the one at position 0
		while (tempProzess.get(0).getAnkuftsZeit()==tempProzess.get(tempCounter).getAnkuftsZeit()) {	
			tempCounter++;
			if (tempCounter==tempProzess.size()) {
				break;
			}
		}
		// range marker: none of the processes will start before that
		for (int i = 0; i < tempProzess.size(); i++) {		
			for (int j = 0; j < finishedTimes[finishedTimes.length-1]; j++) {
				boolean [] mark=new boolean[finishedTimes[finishedTimes.length-1]];
				mark[j]=false;
				tempProzess.get(i).setMarks(mark);
			}
		}
		//line 203 -->sort by Laufzeit if there are any
		if (tempCounter>1) {
			ArrayList<Prozess>rePosition=new ArrayList<>();

			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));
			}
			System.out.println(rePosition.size());
			rePosition.sort(Comparator.comparing(Prozess::getLaufZeit));			//sort by LaufZeit
	
			for (int i = 0; i < rePosition.size(); i++) {
				System.out.println(rePosition.get(i).getLaufZeit());
				tempProzess.set(i, rePosition.get(i));
				buffer.add(rePosition.get(i));										//add it to a temporary Container
			}
		}
		else {																		//else just add the first one to the container
			buffer.add(tempProzess.get(0));									
		}
		int start=buffer.get(0).getAnkuftsZeit();
	//start of srtf
	for (int i = start; i < finishedTimes[finishedTimes.length-1]; i++) {
		if (i<clone .get(0)) {
			//i smaller than the next check point
			if (buffer.get(0).getLaufZeit()>0) {										//laufZeit greater than 0
				buffer.get(0).setLaufZeit(buffer.get(0).getLaufZeit()-1);
				buffer.get(0).getMarks()[i]=true;
			}
			else if (buffer.get(0).getLaufZeit()==0) {									//equals 0 but still not at the checkpoint
				buffer.get(0).setCheck(false);											
				for (int j = 0; j < tempProzess.size(); j++) {							//mark it finished and give the data to the main container
					if (tempProzess.get(j).getObjectId()==buffer.get(0).getObjectId()) {
						tempProzess.get(j).setMarks(buffer.get(0).getMarks());
						tempProzess.get(j).setCheck(buffer.get(0).isCheck());
						break;
					}
				}
				if (buffer.size()>1) {													//if there are more than 1 process in the temporary container 
					buffer.remove(0);
				}
				else if (buffer.size()==1&&clone.size()>1) {							//if there is only 1 but the size of checkpoint is still greater than 1
					for (int j = 0; j < tempProzess.size(); j++) {						
						if (clone.size()>1) {											
							if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {	//add all the prozess that have the same ankunftsZeit as the checkpoint
								buffer.add(tempProzess.get(j));
							}
						}
					}																				
					buffer.remove(0);													//remove the finished 1
					i=clone.get(0);														
					buffer.sort(Comparator.comparing(Prozess::getLaufZeit));			//sort by laufZeit
				}
				i-=1;
			}
		}
		//i equals check point
		else if (i==clone.get(0)) {
			//add all the process with the same ankunftsZeit as checkpoint
			if (clone.size()>1) {
			for (int j = 0; j < tempProzess.size(); j++) {
				if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {
					buffer.add(tempProzess.get(j));
				}
			}
			//sort the container with the processes and remove the checkpoint
			buffer.sort(Comparator.comparing(Prozess::getLaufZeit));
			clone.remove(0);
			i-=1;
			}
		}
	}
	//----------------srtf-----------------------

	//waittime
	for (int j = 0; j < tempProzess.size(); j++) {
		int counter=0;
		for (int j2 = 0; j2 < tempProzess.get(j).getMarks().length; j2++) {
			if (tempProzess.get(j).getMarks()[j2]==true) {
				counter++;
			}
		}
		tempProzess.get(j).setLaufZeit(counter);
		for (int k = 0; k < sizes.size(); k++) {
			if (sizes.get(k).getObjectId()==tempProzess.get(j).getObjectId()) {
				sizes.get(k).setLaufZeit(counter);
			}
		}
	}
	//count the false marks from ankunftsZeit untilits finished = wait time
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
	//------------waittime-------------------------------
	//Print
			int length=finishedTimes[finishedTimes.length-1];
			gantSRTFLRTF(tempProzess,length,2);
	}
	//longest remaining time first
	public void LRTF(ArrayList<Prozess>sizes){
		ArrayList<Prozess>tempProzess=new ArrayList<>(sizes);
		ArrayList<Integer>ankunftsZeit=new ArrayList<>();
		ArrayList<Integer>clone=new ArrayList<>();
		ArrayList<Prozess>buffer=new ArrayList<>();
		int tempCounter=1;
		//warm up session
		for (int i = 0; i < tempProzess.size(); i++) {
			ankunftsZeit.add(i, tempProzess.get(i).getAnkuftsZeit());
		}
		for (int i = 0; i < ankunftsZeit.size()-1; i++) {												//separate ankunfsZeit 
			if (ankunftsZeit.get(i)==ankunftsZeit.get(i+1)) {
				ankunftsZeit.set(i, null);
			}
		}
		for (int i = 0; i < ankunftsZeit.size(); i++) {													//save ankunftsZeit 
			if (ankunftsZeit.get(i)!=null) {
				clone.add(ankunftsZeit.get(i));
			}
		}
		//calculation of finished time
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
		//count the amount of processes that have the same ankunftsZeit as process at 0
		while (tempProzess.get(0).getAnkuftsZeit()==tempProzess.get(tempCounter).getAnkuftsZeit()) {	
			tempCounter++;
			if (tempCounter==tempProzess.size()) {
				break;
			}
		}
		//range marker: see srtf
		for (int i = 0; i < tempProzess.size(); i++) {		
			for (int j = 0; j < finishedTimes[finishedTimes.length-1]; j++) {
				boolean [] mark=new boolean[finishedTimes[finishedTimes.length-1]];
				mark[j]=false;
				tempProzess.get(i).setMarks(mark);
			}
		}
		//if there are processes with the same ankunftsZeit as 0 they will be sorted and reversed
		if (tempCounter>1) {
			ArrayList<Prozess>rePosition=new ArrayList<>();
			for (int i = 0; i < tempCounter; i++) {
				rePosition.add(i, tempProzess.get(i));
			}
			rePosition.sort(Comparator.comparing(Prozess::getLaufZeit));		//sort
			Collections.reverse(rePosition);									//reversed
			//reverse the processes that have the same LaufZeit as that of 0
			int tempC=1;
			if (rePosition.size()>1) {
				while (rePosition.get(0).getLaufZeit()==rePosition.get(tempC).getLaufZeit()) {
					tempC++;
					if (tempC==rePosition.size()) {
						break;
					}
				}
				if (tempC>1) {
					ArrayList<Prozess>reP=new ArrayList<>();
					for (int k = 0; k < tempC; k++) {
						reP.add(k, rePosition.get(k));
					}
					Collections.reverse(reP);									//reverse
					for (int k = 0; k < reP.size(); k++) {
						rePosition.set(k, reP.get(k));
					}
				}
			}
			for (int i = 0; i < rePosition.size(); i++) {
				tempProzess.set(i, rePosition.get(i));
				buffer.add(rePosition.get(i));
			}
		}
		//if none then just add the first 1 into the temporary container
		else {
			buffer.add(tempProzess.get(0));
		}
		int start=buffer.get(0).getAnkuftsZeit();
	
	//lrtf
	for (int i = start; i < finishedTimes[finishedTimes.length-1]; i++) {
		if (i<clone .get(0)) {
			//i smaller than the check point
			if (buffer.get(0).getLaufZeit()>0) {													//laufZeit greater than 1
				buffer.get(0).setLaufZeit(buffer.get(0).getLaufZeit()-1);
				buffer.get(0).getMarks()[i]=true;
			}
			else if (buffer.get(0).getLaufZeit()==0) {												//laufZeit equals 0
				buffer.get(0).setCheck(false);																				
				for (int j = 0; j < tempProzess.size(); j++) {										//marks as finished and delivered to the main container
					if (tempProzess.get(j).getObjectId()==buffer.get(0).getObjectId()) {
						tempProzess.get(j).setMarks(buffer.get(0).getMarks());
						tempProzess.get(j).setCheck(buffer.get(0).isCheck());
						break;
					}
				}
				//temporary container size is greater than 1
				if (buffer.size()>1) {
					buffer.remove(0);															//remove the first 1
					int tempC=1;
					
					//reverse all the processes that have the same laufZeit as that of 0
					if (buffer.size()>1) {
					buffer.sort(Comparator.comparing(Prozess::getLaufZeit));					//sort
					Collections.reverse(buffer);												//reverse
					
						while (buffer.get(0).getLaufZeit()==buffer.get(tempC).getLaufZeit()) {
							tempC++;
							if (tempC==buffer.size()) {
								break;
							}
						}
					}
					if (tempC>1) {
						ArrayList<Prozess>reP=new ArrayList<>();
						for (int k = 0; k < tempC; k++) {
							reP.add(k, buffer.get(k));
						}
						reP.sort(Comparator.comparing(Prozess::getObjectId));
						reP.sort(Comparator.comparing(Prozess::getAnkuftsZeit));
						for (int k = 0; k < reP.size(); k++) {
							buffer.set(k, reP.get(k));
						}
					}
				}
				//size of temporary container=0 and the checkpoint size is greater than 1
				else if (buffer.size()==1&&clone.size()>1) {																
					for (int j = 0; j < tempProzess.size(); j++) {
						if (clone.size()>1) {													//add all the processes with ankunftsZeit that equals checkpoint
							if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {
								buffer.add(tempProzess.get(j));
							}
						}
					}
					buffer.remove(0);															//remove the process at 0
					i=clone.get(0);
					buffer.sort(Comparator.comparing(Prozess::getLaufZeit));					//sort by laufZeit
					Collections.reverse(buffer);												//reverse
					//reverse all the processes that have the same laufZeit as that of 0
					int tempC=1;
					if (buffer.size()>1) {
						while (buffer.get(0).getLaufZeit()==buffer.get(tempC).getLaufZeit()) {
							tempC++;
							if (tempC==buffer.size()) {
								break;
							}
						}
					}
					if (tempC>1) {
						ArrayList<Prozess>reP=new ArrayList<>();
						for (int k = 0; k < tempC; k++) {
							reP.add(k, buffer.get(k));
						}
						reP.sort(Comparator.comparing(Prozess::getAnkuftsZeit));
						for (int k = 0; k < reP.size(); k++) {
							buffer.set(k, reP.get(k));
						}
					}
				}
				i-=1;
			}
		}
		//i equals check point
		else if (i==clone.get(0)) {
			if (clone.size()>1) {													//add all the processes that have ankunftsZeit equals checkpoint
				Prozess tempP=buffer.get(0);
			for (int j = 0; j < tempProzess.size(); j++) {
				if (tempProzess.get(j).getAnkuftsZeit()==clone.get(0)) {
					buffer.add(tempProzess.get(j));
				}
			}
			buffer.sort(Comparator.comparing(Prozess::getLaufZeit));				//sort 
			Collections.reverse(buffer);											//reverse
			//sort all by ankunftsZeit
			int tempC=1;
			if (buffer.size()>1) {
				while (buffer.get(0).getLaufZeit()==buffer.get(tempC).getLaufZeit()) {
					tempC++;
					if (tempC==buffer.size()) {
						break;
					}
				}
			}
			if (tempC>1) {
				ArrayList<Prozess>reP=new ArrayList<>();
				for (int k = 0; k < tempC; k++) {
					reP.add(k, buffer.get(k));
				}
				reP.sort(Comparator.comparing(Prozess::getAnkuftsZeit));			
				for (int j = 0; j < reP.size(); j++) {								//if tempP is in the list
					if (tempP==reP.get(j)) {
						reP.remove(j);
						reP.add(0,tempP);											//tempP will be first
						break;
					}
				}
				for (int k = 0; k < reP.size(); k++) {
					buffer.set(k, reP.get(k));
				}
			}
			clone.remove(0);														//remove checkpoint
			i-=1;
			}
		}
	}
	//----------------lrtf-----------------------

	//waittime
	for (int i = 0; i < tempProzess.size(); i++) {
		int count=0;
		int lauf=0;
		for (int j = tempProzess.get(i).getAnkuftsZeit(); j < tempProzess.get(i).getMarks().length; j++) {
			if (tempProzess.get(i).getMarks()[j]==true) {														//count the true 
				lauf++;
				if (lauf==tempProzess.get(i).getTempLauf()) {													//if the amount of true equals laufZeit break 
					break;
				}
			}																											
			else if (tempProzess.get(i).getMarks()[j]==false) {													//until then count the false 
				count++;
			}
		}
		tempProzess.get(i).setWarteZeit(count);																	//=wait time
	}
	//------------waittime-------------------------------
	//Print
			int length=finishedTimes[finishedTimes.length-1];
			gantSRTFLRTF(tempProzess,length,3);
	}
	//Print Data
	public void gantSRTFLRTF(ArrayList<Prozess>tempProzess,int length,int t){
		System.out.println();
		switch (t) {
		case 2:
			System.out.println("Shortest Remaining Time First");
			break;

		case 3:
			System.out.println("Longest Remaining Time First");
			break;

		default:
			break;
		}
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
		printData(tempProzess,t);
	}
	public void printData(ArrayList<Prozess>tempProzess,int t){
		System.out.print("\n\n");
		switch (t) {
		case 0:
			System.out.println("Shortest Job First");
			break;
			
		case 1:
			System.out.println("First Come First Served");
			break;

		case 2:
			System.out.println("Shortest Remaining Time First");
			break;

		case 3:
			System.out.println("Longest Remaining Time First");
			break;

		default:
			break;
		}
		
		for (int i = 0; i < tempProzess.size(); i++) {												
			System.out.println("Prozess id: "+tempProzess.get(i).getId()+", Ankunftszeit: "+tempProzess.get(i).getAnkuftsZeit()+", Cpu Burst time: "+tempProzess.get(i).getTempLauf()+", Wait time: "+tempProzess.get(i).getWarteZeit()+", Laufzeit: "+(tempProzess.get(i).getTempLauf()+tempProzess.get(i).getWarteZeit()));
		}
		System.out.println("Average Waittime: " +averageWait(tempProzess));
		System.out.println("Average LaufZeit: "+averageLauf(tempProzess));
	}
	
	//calculation of average wait time
	public double averageWait(ArrayList<Prozess>prozess){
		double avgWait=0;
		for (int i = 0; i < prozess.size(); i++) {
			avgWait=avgWait+prozess.get(i).getWarteZeit();
		}
		avgWait/=prozess.size();
		avgWait=(int)avgWait + (Math.round(Math.pow(10,2)*(avgWait-(int)avgWait)))/(Math.pow(10,2)); //rounded to 2 decimals behind the ,
		return avgWait; 
	}
	//calculation of average burst time
	public double averageLauf(ArrayList<Prozess>prozess){
		double avgLauf=0;
		for (int i = 0; i < prozess.size(); i++) {
			avgLauf=avgLauf+prozess.get(i).getTempLauf()+prozess.get(i).getWarteZeit();
		}
		avgLauf/=prozess.size();
		avgLauf=(int)avgLauf + (Math.round(Math.pow(10,2)*(avgLauf-(int)avgLauf)))/(Math.pow(10,2)); //rounded to 2 decimals behind the ,
		return avgLauf;
	}
	//length of tabs for sjf and fcfs
	public void tempLength(int ankunftsTime){												
		int tempLength=ankunftsTime;
		for (int k = 0; k < tempLength; k++) {
			System.out.print(" "+"\t");
		}
	}
}
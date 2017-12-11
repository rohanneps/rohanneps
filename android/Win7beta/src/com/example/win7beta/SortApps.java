package com.example.win7beta;

public class SortApps {
	public void sort(MyApp.Pac[] pacs){
		int i,j;
		MyApp.Pac temp;
		for(i=0;i<pacs.length;i++){
			for(j=i;j<pacs.length;j++){
				if(pacs[i].label.compareToIgnoreCase(pacs[j].label)>0){
					temp=pacs[i];
					pacs[i]=pacs[j];
					pacs[j]=temp;
				}
			}
		}
	}
}

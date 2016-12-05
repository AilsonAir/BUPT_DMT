package cn.edu.bupt.sdmda.cls12;

import java.util.HashMap;
import java.util.LinkedList;

import jm.audio.io.AudioFileIn;
import jm.audio.io.AudioFileOut;
import jm.util.Play;


public class Cls12 {
	private float[] data;
	private int channel;
	private int bitDepth;
	private int sampleRate;
	
	public Cls12(String filePath) {
		AudioFileIn afi = new AudioFileIn(filePath);
		data = afi.getSampleData();
		channel = afi.getChannels();
		bitDepth = afi.getBitResolution();
		sampleRate = afi.getSampleRate();
	}
	
	public Cls12(float[] d, int c, int b, int r) {
		this.data = d;
		this.channel = c;
		this.bitDepth = b;
		this.sampleRate = r;
	}
	
	public float[] getData() {
		return data;
	}

	public int getChannel() {
		return channel;
	}

	public int getBitDepth() {
		return bitDepth;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void write(String fileName) {
		new AudioFileOut(data, fileName, channel, sampleRate, bitDepth);
	}
	
	public void write(String fileName, int channel, int sampleRate,
			int bitDepth) {
		new AudioFileOut(data, fileName, channel, sampleRate, bitDepth);
	}
	
	public void play(){
		String tempname = "temp.aiff";
		new AudioFileOut(data, tempname, channel, sampleRate, bitDepth);
		Play.au(tempname,false);
	}
	
	public static float[] hanning(int size){
		float[] ret = new float[size];
		for(int i=0;i<size;i++){
			ret[i] = (float) (0.5-0.5*Math.cos(2*Math.PI*i/size));
		}
		return ret;
	}
	public float[] ola(float rate){
		int wndSz = sampleRate/10;
		int anaStep = wndSz/2;
		int synStep = (int) (anaStep*rate);
		int numWnd = (int) Math.ceil((data.length-wndSz)/anaStep+1);
		float[] hanningWnd = hanning(wndSz);
		float[][] frames = new float[numWnd][];
		for(int i= 0;i<numWnd;++i){
			frames[i] = new float[wndSz];
		}
		for(int i=0;i<numWnd;++i){
			for(int j=0;j<wndSz;++j){
				try{
				    frames[i][j] = data[i*anaStep+j]*hanningWnd[j];
				}catch(ArrayIndexOutOfBoundsException e){
					frames[i][j] = 0;
				}
			}
		}
		int N = wndSz+(numWnd-1)*synStep;
		float[] ret = new float[N];
		for(int i=0;i<N;++i){
			ret[i] = 0;
		}
		for(int i=0;i<numWnd;++i){
			for(int j=0;j<wndSz;++j){
				ret[i*synStep+j]+=frames[i][j];
			}
		}
		return ret;
	}
	
	public float[] resample(float rate){
		if(1==rate){
			return data;
		}
		float[] ret = new float[(int)(Math.ceil((data.length-1)*rate+1))];
		for(int i=1;i<data.length;i++){
			int newLoc = (int)(Math.ceil(i*rate));
			int start = (int)(Math.ceil((i-1)*rate+1));
			for(int j=start;j<newLoc;++j){
			    int n = newLoc-start;
				ret[j]=((j-start)/n)*data[i]+((newLoc-j)/n)*data[i-1];
				//ret[j]=0;
			}
			ret[newLoc] = data[i];
		}
		return ret;
	}
	
	public float[] filter(float[] b, float[] a){
		LinkedList<Float> qa=new LinkedList<Float>();
		LinkedList<Float> qb=new LinkedList<Float>();
		
		HashMap<Integer, Float> ha = new HashMap<Integer, Float>();
		HashMap<Integer, Float> hb = new HashMap<Integer, Float>();
		
		for(int i=1;i<b.length;i++){
			qb.add(0f);
			if(b[i]!=0){
				hb.put(i, b[i]);
				
			}
			
		}
		for(int i=1;i<a.length;i++){
			qa.add(0f);
			if(a[i]!=0){
				ha.put(i, a[i]);
				
			}
		}
		float[] y = new float[data.length];
		for(int i = 0;i<data.length;i++){
			float cur = data[i];
			float res = cur*b[0];
			//y += b[j]*qb[L-j], j=1,¡­L
		/*	for(int j=1;j<b.length;j++){
				res += qb.get(b.length-j-1)*b[j];
				
			}*/
			for(Integer k:hb.keySet()){
				res += qb.get(b.length-k-1)*hb.get(k);
			}
			//y += a[j]*qa[N-j], j=1,¡­N
			/*for(int j=1;j<a.length;j++){
				res -= qa.get(a.length-j-1)*a[j];
				
			}*/
			for(Integer k:ha.keySet()){
				res -= qa.get(a.length-k-1)*ha.get(k);
			}
			qb.add(cur);
			qb.remove(0);
			qa.add(res);
			qa.remove(0);
			y[i]=res;
		}
		return y;
	}

}


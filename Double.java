/*TEST
- data NIM tidak boleh sama
- data otomatis akan disimpan secara terurut(ascending) sesuai dengan Jenis Kelamin lalu kemudian diurutkan lagi berdasarkan NIM
- pencarian data(boleh berdasar NIM atau Nama atau keduanya, bebas)
- menghapus data
- edit data (Nama dan atau NIM, atau data lainnya jika anda menambah data lain)
- untuk melihat data, data ditampilkan dalam bentuk tabel
*/

import java.util.Scanner;

class Node {
	public String nama;
	public String nim;
	public String jeniskelamin;
	public Node next;		//pointer(link)untuk terhubung dengan Node berikutnya
	public Node previous;	//pointer(link)untuk terhubung dengan Node sebelumnya
// -------------------------------------------------------------
	public Node(String nimprtma, String namakdua, String jktiga) {
		nim = nimprtma;
		nama = namakdua;
		jeniskelamin = jktiga;
	}
	        
	public void tampilNode() {
		System.out.printf("| %11s | %-16s |   %2s    |\n",nim,nama,jeniskelamin);
	}
	
// -------------------------------------------------------------
	public void tampil(){
		System.out.println( "NIM	: " + nim );
		System.out.println( "NAMA	: " + nama ); 
		System.out.println( "KELAMIN   : " + jeniskelamin );
	}
} // kelas node

class DoubleLink {
	private Node first; // untuk menunjuk awak list
	private Node last;  // untuk menunjuk ke akhir list
	private Node letak;
// -------------------------------------------------------------
	public DoubleLink() { // konstruktor
		first = null; //inisialisasi pointer awal
		last = null;  //inisialisasi pointer akhir
	}
// -------------------------------------------------------------
	public boolean isEmpty() { //true jika tidak ada data
		return first==null;
	}

   public void insertFirst(String nimprtma, String namakdua, String jktiga ){
		Node newNode = new Node(nimprtma, namakdua, jktiga);
		if(isEmpty())
			last = newNode;
		else
			first.previous = newNode;
		newNode.next = first;
		first = newNode;
	}

	public void insertLast(String nimprtma, String namakdua, String jktiga) {
		Node newNode = new Node(nimprtma,namakdua, jktiga);
		if( isEmpty() )
			first = newNode;
		else {
			last.next = newNode;
			newNode.previous = last;
		}
		last = newNode;
	}

     
	public Node hapusFirst() {
		Node temp = first;
		if(first.next == null)
			last = null;
		else
			first.next.previous = null;
		first = first.next;
		return temp;
	}
	
       
	public Node hapusLast() {
		Node temp = last;
		if(first.next == null)
			first = null;
		else
			last.previous.next = null;
		last = last.previous;
		return temp;
	}
	
       
	public boolean insertAfter(String dat, String nimprtma, String namakdua, String jktiga) {
		Node indek = first;
		while(indek.nim.compareTo(dat) != 0) {
			indek = indek.next;
			if(indek == null)
				return false; // data tidak ditemukan
		}
		Node newNode = new Node(nimprtma,namakdua, jktiga);
		if(indek == last) {
			newNode.next = null;
			last = newNode;
		}
		else {
			newNode.next = indek.next;
			indek.next.previous = newNode;
		}
		newNode.previous = indek;
		indek.next = newNode;
		return true; // data ditemukan dan data baru dimasukkan
	}
	
     
	public boolean insertBefore(String dat, String nimprtma, String namakdua, String jktiga) {
		Node indek = first;
		while(indek.nim.compareTo(dat) != 0) {
			indek = indek.next;
			if(indek == null)
				return false; // data tidak ditemukan
		}
		Node newNode = new Node(nimprtma,namakdua, jktiga);
		if(indek == first) {
			newNode.next = indek;
			first=newNode;
		}
		else {
			newNode.next = indek;
			newNode.previous = indek.previous;
			indek.previous.next=newNode;
		}
		indek.previous=newNode;
		return true; // data ditemukan dan data baru dimasukkan
	}
	   
	public Node hapus(String dat) {
		Node indek = first;
		while(indek.nim.compareTo(dat) != 0) {
			indek = indek.next;
			if(indek == null)
				return null; // data tidak ditemukan
		}
		if(indek == first)
			first = indek.next;
		else
			indek.previous.next = indek.next;
		
		if(indek == last)
			last = indek.previous;
		else
			indek.next.previous = indek.previous;
		return indek;
	}
	
// Ascending-------------------------------------------------------------
	public void tampilMaju() {
		System.out.println("");
		System.out.println("		TABEL DATA MAHASISWA");
		System.out.format("\n-------------------------------------------------%n");
		System.out.printf("| NO |       NIM   |    NAMA         | KELAMIN  |%n");
		System.out.format("+----+-------------+-----------------+----------+%n");
		Node indek = first;
		int i=0;
		while(indek != null) {
			System.out.printf("| %2d ",++i);
			indek.tampilNode();
			indek = indek.next;
		}
		System.out.println("-------------------------------------------------");
	}
	
	/*public void tampilMundur() {
		System.out.println("");
		System.out.println("		TABEL DATA MAHASISWA");
		System.out.format("\n-------------------------------------------------%n");
		System.out.printf("| NO |       NIM   |    NAMA         | KELAMIN  |%n");
		System.out.format("+----+-------------+-----------------+----------+%n");
		Node indek = last;
		int i=0;
		while(indek != null) {
			System.out.printf("| %2d ",++i);
			indek.tampilNode();
			indek = indek.previous;
		}
		System.out.println("-------------------------------------------------");
	}*/
	
	public void clear(){
		while(!isEmpty()){
			hapusFirst();
		}
	}
	
//cek nim sudah ada atau belum
	public boolean cekNIM(String apa){
		Node indek = first;
		boolean hasil = false;
		
		while(indek != null){
			if(apa.compareTo(indek.nim)==0){
				hasil=true; 
				this.letak=indek; 
			}
			indek = indek.next; 
		}
		return hasil; // mengembalikan nilai, bernilai TRUE jika ada yang sama
	}
	
//menampilkan hasil dari cek
	public void tampilCari(String apa){
		if(cekNIM(apa)==true){
			System.out.println("\nData Ditemukan!");
			this.letak.tampil();
		}
		else System.out.println("\nData tidak ditemukan!");
	}
	
//cek nama sudah ada atau belum
	public boolean cekNama(String apa){
		Node indek = first;
		boolean hasil = false;
		
		while(indek != null){
			if(apa.compareTo(indek.nama)==0){
				hasil=true; 
				this.letak=indek; 
			}
			indek = indek.next; 
		}
		return hasil;
	}
	
//menampilkan hasil dari cek
	public void tampilCariNama(String apa){
		if(cekNama(apa)==true){
			System.out.println("\nData Ditemukan");
			this.letak.tampil();
		}
		else System.out.println("\nData tidak ditemukan");
	}
	
//edit data
	public void editNode(String nimprtma, String namakdua, String jktiga) {
		Node newNode = new Node(nimprtma,namakdua, jktiga);
		
		if(isEmpty()){ 		
			hapusFirst();
			insertFirst(nimprtma, namakdua, jktiga);
		}
		else if(this.letak==first){	
			hapusFirst();
			insertFirst(nimprtma, namakdua, jktiga);
		}
		else if(this.letak==last){	
			hapusLast();
			insertLast(nimprtma, namakdua, jktiga);
		}
		else{		//data ditengah
			newNode.next = this.letak.next;
			this.letak.next.previous = newNode;
			newNode.previous = this.letak.previous;
			this.letak.previous.next = newNode;
		}
	}
	
	public void Sorting(String nimprtma, String namakdua, String jktiga){
		Node indek	= first;
		Node indek2	= last;

		if(isEmpty()){
			insertFirst(nimprtma, namakdua, jktiga);
		}
		else if(jktiga.compareTo(indek.jeniskelamin) < 0){
			insertFirst(nimprtma, namakdua, jktiga);
		}
		else if(jktiga.compareTo(indek.jeniskelamin) > 0){
				if (indek2.jeniskelamin.equalsIgnoreCase("W")){
					while(nimprtma.compareTo(indek2.nim)<0 && indek2.jeniskelamin.equalsIgnoreCase("W")){
						indek2 = indek2.previous;
					}
						insertAfter(indek2.nim, nimprtma, namakdua, jktiga);	
				} 
				else{
					insertLast(nimprtma, namakdua, jktiga);
				}
			}
				else {
					if (jktiga.equalsIgnoreCase("L") && indek2.jeniskelamin.equalsIgnoreCase("W")) {
					while(nimprtma.compareTo(indek.nim)>0 && indek != indek2 && indek.jeniskelamin.equalsIgnoreCase("L") )
					{indek = indek.next;}

					insertBefore(indek.nim, nimprtma, namakdua, jktiga);
			}
			else if (jktiga.equalsIgnoreCase("L")) {
				while(nimprtma.compareTo(indek.nim)>0 && indek != indek2){
						indek = indek.next;
					}

				if (nimprtma.compareTo(indek.nim)>0){
					insertAfter(indek.nim, nimprtma, namakdua, jktiga);	
				} else{
					insertBefore(indek.nim, nimprtma, namakdua, jktiga);
				}
			}
			else {
				while(nimprtma.compareTo(indek.nim)>0 && indek != indek2){
						indek = indek.next;
					}
				if (nimprtma.compareTo(indek.nim)>0){
					insertAfter(indek.nim, nimprtma, namakdua, jktiga);	
				} else{
					insertBefore(indek.nim, nimprtma, namakdua, jktiga);
				}
			}

		}
	}
	
//untuk melihat letak
	public int pos(){	
		int i=0;
		Node indek=this.letak;
		while(indek != null) {
			i++;
			indek = indek.next;
		}
		return i;
	}
	
} // end class DoubleLink

class Double {
	public static void main(String[] args) {
		DoubleLink list = new DoubleLink();
		byte insert=0, delete=0, lihat=0, search=0, menu=0;
		String d1,d2;
		String d3="";
		boolean ulang=true;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		do {
			System.out.println("-----------------------------");
			System.out.println("|  Program Double Link List |");
			System.out.println("-----------------------------");
			System.out.println("|  Pilihan Menu :           |");
			System.out.println("|   1. Masukkan Data        |");
			System.out.println("|   2. Hapus Data           |");
			System.out.println("|   3. Menampilkan data     |");
			System.out.println("|   4. Edit data            |");
			System.out.println("|   5. Keluar               |");
			System.out.println("-----------------------------");
			ulang=true;
			while(ulang){
				try{
					System.out.print("Anda memilih : ");
					menu = input.nextByte();
					System.out.print("");
					ulang=false;
				}catch(Exception ex){
					System.out.println("masukkan nomor pilihan!\n");
					input.nextLine();
				}
			}
			
			switch(menu) {
				//input data
				case 1 : {
					System.out.println("");
					System.out.print("Masukkan NIM                 = ");
	  		    	d1 = input.next();
					if(list.cekNIM(d1)) {
						System.out.println("\nNIM sudah ada!"); 
						break;
					}
	  		    	System.out.print("Masukkan Nama Anda           = ");
	  		    	d2 = input2.nextLine();
					
					ulang=true;
					while(ulang){
						try{
							System.out.print("Masukkan Jenis Kelamin (L/W) = ");
							d3 = input.next();
							if(d3.equalsIgnoreCase("L") || d3.equalsIgnoreCase("W"))
								ulang=false; //keluar
							else
								System.out.println("Jenis KELAMIN salah");
						}catch(Exception e){
							System.out.println("input salah !!");
							input.next();
						}
					}
					list.Sorting(d1, d2, d3);
					break;
				}
				//Menghapus data 
 				case 2: {
								list.tampilMaju();
								System.out.println("");
								System.out.print("NIM yang akan dihapus  = ");
								String d0 = input.next();
								if (list.hapus(d0)!=null)
									System.out.println("Data " + d0 + " berhasil dihapus!");
								else
									System.out.println("data " + d0 + " tidak ditemukan");
								}break;
				//Menampilkan data
				case 3: {
					list.tampilMaju();
				}break;

				//Edit data
				case 4: {
					do{
						System.out.println("\tMengedit Data: ");
						System.out.println("\t1. Nama");
						System.out.println("\t2. NIM");
						System.out.println("\t3. Keluar");
						try{
							System.out.print("\tPilihan =>  ");
							search=input.nextByte();
						}catch (Exception ee){
							System.out.println("\tmasukkan nomor pilihan!!\n");
							input.nextLine();
						}
						switch (search) {
							case 1 : {
								if(list.isEmpty()) 
								System.out.println("Data kosong! Masukkan data terlebih dahulu!");
								else{
									System.out.print("Masukkan Nama yang ingin diedit = ");
									String edit = input2.nextLine();
									
									if(list.cekNama(edit)){
										list.tampilCariNama(edit);
										System.out.println("");
										System.out.print("Masukkan NIM        	= ");
										d1 = input.next();
										if(list.cekNIM(d1)) {
											System.out.println("NIM sudah ada!"); 
											break;
										}
										System.out.print("Masukkan Nama (8 char)	= ");
										d2 = input2.nextLine();
											
										ulang=true;
										while(ulang){
											try{
												System.out.print("Masukkan Jenis Kelamin 		= ");
												d3 = input.next();
												if(d3.equalsIgnoreCase("L") || d3.equalsIgnoreCase("W"))
													ulang=false; //keluar
												else
													System.out.println("Jenis KELAMIN salah");
											}catch(Exception e){
												System.out.println("input salah ...");
												input.next();
											}
										}
										list.editNode(d1,d2,d3); // edit data
									}
								}
								break;
							}
							case 2 : {
								if(list.isEmpty()) 
								System.out.println("Data kosong! Masukkan data terlebih dahulu!");
								else{
									System.out.print("Masukkan NIM yang ingin diedit = ");
									String edit = input2.nextLine();
									
									if(list.cekNIM(edit)){
										list.tampilCari(edit);
										System.out.println("");
										System.out.print("Masukkan NIM        	= ");
										d1 = input.next();
										if(list.cekNIM(d1)) {
											System.out.println("NIM sudah ada!"); 
											break;
										}
										System.out.print("Masukkan Nama (8 char)	= ");
										d2 = input2.nextLine();
										
										ulang=true;
										while(ulang){
											try{
												System.out.print("Masukkan Jenis Kelamin 		= ");
												d3 = input.next();
												if(d3.equalsIgnoreCase("L") || d3.equalsIgnoreCase("W"))
													ulang=false; //keluar
												else
													System.out.println("Jenis KELAMIN salah");
											}catch(Exception e){
												System.out.println("input salah ...");
												input.next();
											}
										}
										
										list.editNode(d1,d2,d3); // edit data
									}
								}
								break;
							}
						}
					}while (search < 0 || search > 3);
 				}break;
			} // end switch
		} while(menu > 0 && menu < 5);
	} // end main()
} // end class Double

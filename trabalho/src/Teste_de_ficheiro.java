import javax.xml.crypto.Data;
import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

public class Teste_de_ficheiro {

    public static int leituraFicheiroCSV (String [] arraio, int[][]valoresAcumulados){
        String caminho = "src/exemplo.csv";
        String linha = "";
        int cont=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(caminho));
            br.readLine(); // para tirar o rotulo da informação
            while((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                arraio[cont]=valores[0];
                for (int i = 0; i < 5; i++) {
                    valoresAcumulados [cont] [i] = Integer.parseInt( valores [i+1] );
                }
                cont++;
            }

        }catch (FileNotFoundException e){
              e.printStackTrace();
          }catch (IOException e){
              e.printStackTrace();
          }
        return cont;
    }

    public static void imprimir_matriz ( int arrio [][] , int contador){
        for (int valorDif = 0; valorDif< 1 ; valorDif++) {
            for (int i = 0; i < contador; i++) {
                System.out.println(arrio[i][valorDif]);
            }
        }
    }

    public static void imprimir_vetor_String ( String []data, int contador){
        for (int i = 0; i < contador; i++) {
            System.out.print(data[i]);
            System.out.println();
        }
    }

    public static int verificarOdiaDaSemana (String data){
        int [] datanova = new int[3];
        int n;
        String [] dataSeparada = data.split("-"); // 0 ano, 1 mes , 2 dia
        datanova[0]= Integer.parseInt(dataSeparada[0]);
        datanova[1]= Integer.parseInt(dataSeparada[1]);
        datanova[2]= Integer.parseInt(dataSeparada[2]);

        Calendar c = Calendar.getInstance();
        c.set(datanova[0],(datanova[1]-1),datanova[2]);
        int [] days = new int[]{1,2,3,4,5,6,7}; // 1=domingo 2=segunda 3=terça etc...
        n = c.get(c.DAY_OF_WEEK);
        return days[n-1];

    }

    public static int comecaSegunda_AcabarDomingo (String data1, int data,int iniOuFim){
        int  diaDaSemana;
        diaDaSemana = verificarOdiaDaSemana(data1);  // este tem que ser = 2
            if (iniOuFim==2){
                switch (diaDaSemana) {
                    case 1:
                        data = data + 1;
                        break;

                    case 2:
                        //tá certo
                        break;

                    case 3:
                        data = data + 6;
                        break;

                    case 4:
                        data = data + 5;
                        break;

                    case 5:
                        data = data + 4;
                        break;

                    case 6:
                        data = data + 3;
                        break;

                    case 7:
                        data = data + 2;
                        break;

                }
            } else {switch (diaDaSemana) {
                case 1:
                    //ta certo
                    break;

                case 2:
                    data = data - 1;
                    break;

                case 3:
                    data = data - 2;
                    break;

                case 4:
                    data = data - 3;
                    break;

                case 5:
                    data = data - 4;
                    break;

                case 6:
                    data = data - 5;
                    break;

                case 7:
                    data = data - 6;
                    break;
                }
            }

            return data;
    }



    public static String trocar_A_Ordem (String data1){
        String [] primeiraDataSeparada = data1.split("-");
        String guardar;
        guardar = primeiraDataSeparada[0];
        primeiraDataSeparada[0] = primeiraDataSeparada[2];
        primeiraDataSeparada[2] = guardar;
        String dataNova = primeiraDataSeparada[0] + "-" + primeiraDataSeparada[1] + "-" + primeiraDataSeparada[2];
        return dataNova;
    }


    public static void procurarAData (String data1 , String data2 , String []datasFicheiro , int cont ,int X ,int [][] valores_Acumulados ){
        int valorDeVerificacao = -1;
        int cont2 = 0;
        for (int DataIni = 0; DataIni < cont; DataIni++) {
            if (datasFicheiro[DataIni].equals(data1)){
                for (int DataFim = DataIni; DataFim <= cont - DataIni ; DataFim++) {
                    if((datasFicheiro[DataFim].equals(data2)) && (cont2>0)){
                        // aqui já encontrei as duas datas no ficheiro, sendo a primeira "DataIni" e a segunda "DataFim"
                        // aqui dentro secalhar criava três novos modulos para calcular a diaria,semanal e mensal
                        switch (X){
                            case 0:
                                calculoDiario(DataIni,DataFim,valores_Acumulados,datasFicheiro);
                                //dia
                                break;

                            case 1:
                                int segunda=2;
                                int domingo=1;
                                DataIni = comecaSegunda_AcabarDomingo(data1,DataIni,segunda);  // transforma a data numa segunda feira pelo ficheiro
                                DataFim = comecaSegunda_AcabarDomingo(data2,DataFim,domingo); // transforma a data num domingo pelo ficheiro
                                calculoSemanal(DataIni,DataFim,valores_Acumulados,datasFicheiro);
                                //semanal
                                break;

                            case 2:
                                //mensal
                                break;

                            default:
                                System.out.println("Tem que inserir 0 , 1 , 2");
                        }
                    }
                    cont2++;


                }
            }
        }
    }

    public static void calculoDiario (int DataIni, int DataFim, int [][] valores_Acumulados, String [] datas){
            for (int j = DataIni; j <= DataFim; j++) {
                if (j  != 0) {
                    System.out.println("Análise do dia " + trocar_A_Ordem(datas[j]));
                    System.out.println("Novos casos diarios: " + (valores_Acumulados[j][1] - valores_Acumulados[j - 1][1]));
                    System.out.println("Novos hospitalizados: " + (valores_Acumulados[j][2] - valores_Acumulados[j - 1][2]));
                    System.out.println("Novos internados UCI: " + (valores_Acumulados[j][3] - valores_Acumulados[j - 1][3]));
                    System.out.println("Novos mortos: " + (valores_Acumulados[j][4] - valores_Acumulados[j - 1][4]));
                    System.out.println();
                }else{
                    System.out.println("Não há dados para a primeira data");
                }
            }
    }

    public static void calculoSemanal (int DataIni, int DataFim, int [][] valores_Acumulados, String []datas){
        int check=-1;
        for (int i = DataIni; i <= DataFim ; i=i+7) {
            if(i+6 <= DataFim) {
                System.out.println("Análise semanal de " + trocar_A_Ordem(datas[i]) + " até " + trocar_A_Ordem(datas[i+6]));
                System.out.println("Novos casos diarios: " + (valores_Acumulados[i + 6][1] - valores_Acumulados[i][1]));
                System.out.println("Novos hospitalizados: " + (valores_Acumulados[i + 6][2] - valores_Acumulados[i][2]));
                System.out.println("Novos internados UCI: " + (valores_Acumulados[i + 6][3] - valores_Acumulados[i][3]));
                System.out.println("Novos mortos: " + (valores_Acumulados[i + 6][4] - valores_Acumulados[i][4]));
                System.out.println();
            }
        }
    }


    public static void main (String[] args){
        Scanner sc= new Scanner(System.in);
        int valor_estupido = 10000;
        String [] datasDoFicheiro= new String[valor_estupido];
        int [][] valores_Acumulados = new int [valor_estupido] [5];
        int contador = leituraFicheiroCSV(datasDoFicheiro,valores_Acumulados);
        //  imprimir_vetor_String(datasDoFicheiro,contador);
       // imprimir_matriz(valores_Acumulados, contador);
        System.out.print("Inserir Data Inicial (dd-mm-aaaa): ");
        String data1 = trocar_A_Ordem(sc.nextLine());
        System.out.println();
        System.out.print("Inserir Data Final (dd-mm-aaaa): ");
        String data2 = trocar_A_Ordem(sc.nextLine());
        int X =sc.nextInt();
       // System.out.println(data1);
       // System.out.println(data2);
        procurarAData (data1 ,data2 , datasDoFicheiro , contador , X , valores_Acumulados );
    }

}
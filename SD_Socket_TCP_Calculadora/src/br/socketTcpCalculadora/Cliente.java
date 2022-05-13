package br.socketTcpCalculadora;
//execute o servidor primeiro, depois execute o Cliente
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    static Conexao c;
    static Socket socket;

    public Cliente() {
        try {
            socket = new Socket("localhost", 9600);
        } catch (Exception e) {
            System.out.println("Nao consegui resolver o host...");
        }
    }

    public static void main(String[] args) {

        new Cliente();
        float op1, op2;
        char oper;
        Scanner in = new Scanner(System.in);

        System.out.println("*********************************");
        System.out.println("***  CALCULADORA DISTRIBUIDA  ***");
        System.out.println("*********************************");

        System.out.println("Digite o primeiro numero");
        op1 = in.nextFloat();
        System.out.println("Digite o segundo numero");
        op2 = in.nextFloat();
        System.out.println("Escolha uma operação");
        System.out.println("(+)SOMA (-)SUBTRACAO (x)MULTIPLICACAO (/)DIVISAO");
        oper = in.next().charAt(0);

        Requisicao msgReq = new Requisicao(op1, op2, oper);
        c.send(socket, msgReq);
        Resposta msgRep = (Resposta) c.receive(socket);

        if (msgRep.getStatus() == 0) {
            System.out.println("Resultado = " + msgRep.getResult());
        } else if (msgRep.getStatus() == 1) {
            System.out.println("Operacao nao Implementada");
        } else {
            System.out.println("Divisao por Zero");
        }

        try {
            socket.close();
        } catch (Exception e) {
            System.out.println("problemas ao fechar socket");
        }
    }
}

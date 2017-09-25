/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comidafrita;

/**
 *
 * @author Esteban
 */
public class ComidaFrita {

    public static void main(String[] args) {
        String prueba = "a";
        String exp=":";
        System.out.println(prueba.matches(exp));
    }

    public static boolean hayVariable(String codigo) {
        int contBusqueda = 0;
        boolean salir = false;
        boolean hayvariable = codigo.contains("D");
        while ((hayvariable) && (!salir)) {
            if ((codigo.compareTo("") == 0) || (!codigo.contains("D")) || (!codigo.contains(";"))) {
                hayvariable = false;
                salir = true;
            } else {
                int index = codigo.indexOf("D");
                codigo = codigo.substring(index);
                index = codigo.indexOf(";");
                String test;
                test = codigo.substring(0, index) + ";";
                codigo = codigo.substring(1);
                //System.out.println("Esto voy a testear como variable:"+test);
                if (revisarDeclaracionVariable(test)) {
                    salir = true;
                }
            }

        }
        return hayvariable;
    }

    public static boolean revisarDeclaracionVariable(String codigo) {
        boolean resultado = true;
        String[] test;
        test = codigo.split(" ");
        if (test.length != 3) {
            resultado = false;
        } else {
            if (!revisarNombresReservados(test[1])) {
                resultado = false;
            }
            if (test[1].substring(0, 1).compareTo(test[1].substring(0, 1).toUpperCase()) != 0) {
                resultado = false;
            }
            if (test[2].substring(test[2].length() - 1, test[2].length()).compareTo(";") != 0) {
                resultado = false;
            }
        }

        return resultado;
    }

    public static boolean revisarIniciarVariables(String codigo) {
        boolean resultado = true;
        String[] test;
        test = codigo.split(" ");
        if (test.length != 4) {
            resultado = false;
        } else {
            System.out.println("test0:" + test[0] + " test1:" + test[1] + " test2:" + test[2] + " test3:" + test[3]);
            if (!revisarNombresReservados(test[1])) {
                resultado = false;
            }
            System.out.println("Prueba1:" + resultado);
            if (test[1].substring(0, 1).compareTo(test[1].substring(0, 1).toUpperCase()) != 0) {
                resultado = false;
            }
            System.out.println("Prueba2:" + resultado);
            if (test[2].compareTo("=") != 0) {
                resultado = false;
            }
            System.out.println("Prueba3:" + resultado);
            if (test[3].substring(test[3].length() - 1).compareTo(";") != 0) {
                resultado = false;
            }
            System.out.println("Prueba4:" + resultado);
        }

        return resultado;
    }
    public static boolean revisarIncrementoDecremento(String codigo)
    {
        boolean resultado=true;
        if(codigo.substring(0,1).compareTo(codigo.substring(codigo.length()-2,codigo.length()-1))!=0)
        {
            resultado=false;
        }
        if (codigo.substring(codigo.length() - 1).compareTo(";") != 0) 
        {
            resultado = false;
        }
        if(codigo.contains(" "))
        {
            resultado=false;
        }
        return resultado;
    }
    public static boolean revisarTurnOn(String codigo)
    {
        String expresionTurnOn="TurnOn\\([0-4],[0-4],[rby]\\)\\;";
        return codigo.matches(expresionTurnOn);
    }
    public static boolean revisarTurnOff(String codigo)
    {
        String expresionTurnOn="TurnOff\\([0-4],[0-4],[rby]\\)\\;";
        return codigo.matches(expresionTurnOn);
    }
    public static boolean revisarTurnOnAll(String codigo)
    {
        String expresionTurnOnAll="TurnOn\\(\\)\\;";
        return codigo.matches(expresionTurnOnAll);
    }
    public static boolean revisarTurnOffAll(String codigo)
    {
        String expresionTurnOnAll="TurnOff\\(\\)\\;";
        return codigo.matches(expresionTurnOnAll);
    }
    public static boolean revisarSoundOn(String codigo)
    {
        String expresionSoundOn="SoundOn\\([0-4][0-4]\\)\\;";
        return codigo.matches(expresionSoundOn);
    }
    public static boolean revisarSoundOff(String codigo)
    {
        String expresionSoundOff="SoundOn\\(\\)\\;";
        return codigo.matches(expresionSoundOff);
    }
    public static boolean revisarDow1(String codigo)
    {
        String nombrevariable="[A-Z][A-Za-z0-9]*";
        String expresionDow="Dow "+nombrevariable+" in "+"[0-9][0-9]* Step [0-9][0-9]*";
        return codigo.matches(expresionDow);
    }
    public static boolean revisarDow(String codigo)
    {
        int indexFinalEncabezado=codigo.indexOf("\n");
        boolean resultado=(revisarDow1(codigo.substring(0,indexFinalEncabezado))&&
                (codigo.contains("Exit")));
        return resultado;
    }
    public static boolean revisarFor1(String codigo)
    {
        String expresionFor="For [0-9][0-9]* Times";
        return codigo.matches(expresionFor);
    }
    public static boolean revisarFor(String codigo)
    {
        return revisarFor1(codigo.substring(0,codigo.indexOf("\n")));
    }
    public static boolean revisarIf1(String codigo)
    {
        String nombrevariable="[A-Z][A-Za-z0-9]*";
        String expresionIf1="If "+nombrevariable+" = Then";
        return codigo.matches(expresionIf1);
    }
    public static boolean revisarIf2(String codigo)
    {
        String nombrevariable="[A-Z][A-Za-z0-9]*";
        String expresionIf2="elseIf "+nombrevariable+" = [0-9][0-9]*";
        return codigo.matches(expresionIf2);
    }
    public static boolean revisarIf(String codigo)
    {
        boolean resultado=true;
        if(!revisarIf1(codigo.substring(0,codigo.indexOf("\n"))))//reviso encabezado
        {
            resultado=false;
        }
        else
        {
            boolean salir=false;
            while((codigo.contains("elseIf"))&&(!salir))//reviso si tiene algun elseIf
            {
                //reviso si el else esta antes de un elseIf
                if((codigo.contains("else"))&&(codigo.indexOf("else")<codigo.indexOf("elseIf")))
                {
                    resultado=false;
                    salir=true;
                }
                //revisar si esta bien escrito el elseIf
                else if(!revisarIf2(codigo.substring(codigo.indexOf("elseIf"),codigo.indexOf("\n"))))
                {
                    resultado=false;
                }
                //continuar analisando
                else if(!salir)
                {
                    codigo=codigo.substring(codigo.indexOf("elseIf")+6);
                }
            }
            //reviso si esta bien escrito el else
            if ((codigo.contains("else"))&&(!salir)) 
            {
                if(codigo.substring(codigo.indexOf("else"),codigo.indexOf("\n")).compareTo("else")!=0)
                {
                    resultado=false;
                } 
            }
        }
        return resultado;
    }
    public static boolean revisarCall(String codigo)
    {//los procedimientos tienen que iniciar con minuscula
        return codigo.matches("Call [a-z][A-Za-z0-9]*");
    }
    public static boolean revisarP1(String codigo)
    {
        return codigo.matches("P :[a-z][A-Za-z0-9]*");
    }
    public static boolean revisarPs(String codigo)
    {
        
        return true;
    }
    public static String Analisis(String codigo)
    {//verificar si tiene comentarios
        if(codigo.substring(0,2).compareTo(">>")==0)
        {
            codigo=codigo.substring(codigo.indexOf("\n")+1);
            //verificar si despues de comentarios estan la definicion de variables
            if(codigo.substring(0,1).compareTo("D")==0)
            {
                //verificar condicion de alguna variable
                if(hayVariable(codigo))
                {
                    
                }
            }
        }
        return codigo;
    }
    public static boolean revisarNombresReservados(String nombre) {
        boolean resultado = true;
        if (nombre.compareTo("D") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("INZ") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("TurnON()") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("TurnOFF()") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("SoundOFF()") == 0) {
            resultado = false;
        }
        if (nombre.compareTo(";") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("Dow") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("in") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("Enddo") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("For") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("FEnd") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("If") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("ElseIf") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("Call") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("else") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("Endif") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("Endif") == 0) {
            resultado = false;
        }
        if (nombre.compareTo("P") == 0) {
            resultado = false;
        }
        return resultado;
    }
}

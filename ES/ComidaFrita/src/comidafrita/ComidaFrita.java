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
        String prueba = "elseif";
        
        System.out.println("resultado:"+prueba.substring(0,6));
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
            //System.out.println("test0:" + test[0] + " test1:" + test[1] + " test2:" + test[2] + " test3:" + test[3]);
            if (!revisarNombresReservados(test[1])) {
                resultado = false;
            }
            //System.out.println("Prueba1:" + resultado);
            if (test[1].substring(0, 1).compareTo(test[1].substring(0, 1).toUpperCase()) != 0) {
                resultado = false;
            }
            //System.out.println("Prueba2:" + resultado);
            if (test[2].compareTo("=") != 0) {
                resultado = false;
            }
            //System.out.println("Prueba3:" + resultado);
            if (test[3].substring(test[3].length() - 1).compareTo(";") != 0) {
                resultado = false;
            }
            //System.out.println("Prueba4:" + resultado);
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
    public static boolean revisarP2(String codigo)
    {
        return codigo.matches("P [a-z][A-Za-z0-9]*\\|");
    }
    public static boolean revisarP3(String codigo)
    {
        boolean resultado=false;
        String nombreI=codigo.substring(codigo.indexOf(":")+1,codigo.indexOf("\n"));
        codigo=codigo.substring(codigo.indexOf("\n"));
        System.out.println("revisandoP3"+codigo.substring(codigo.indexOf("P ")+2));
        String nombreF=codigo.substring(codigo.indexOf("P ")+2,codigo.indexOf("|"));
        System.out.println(nombreI+":"+nombreF);
        return nombreI.compareTo(nombreF)==0;
    }
    public static boolean revisarP(String codigo)
    {
        //revisa si el encabezado esta bien p1 si el final esta bien p2 y si el encabezado==final
        boolean resultado=true;
        System.out.println("que me llega"+codigo);
        String toP2=codigo.substring(codigo.indexOf("\n")+1);
        if(!revisarP1(codigo.substring(0,codigo.indexOf("\n"))))
        {
            resultado=false;
            System.out.println(resultado+"1");
        }
        else if(!revisarP2(toP2.substring(toP2.indexOf("P "))))
        {
            resultado=false;
            System.out.println(resultado+"2");
        }
        else if(!revisarP3(codigo))
        {
            resultado=false;
            System.out.println(resultado+"3");
        }
        return resultado;
    }
    public static String revisarPs(String codigo)
    {
       boolean salir=false;
       while((codigo.compareTo("")!=0)&&(!salir))
       {
           //revisar si tiene final 
           if(codigo.substring(1).contains("P "))
           {
               System.out.println("revisando!"+codigo.substring(0,codigo.indexOf("|")+1));
               
               if (revisarP(codigo.substring(0,codigo.indexOf("|")+1))) 
               {
                   codigo=codigo.substring(codigo.indexOf("|")+1);
                   if(codigo.length()>0)
                   {
                       codigo=codigo.substring(1);
                   }
               }
               else{salir=true;}
           }
           else{salir=true;}
           
       }
       return codigo;
    }
    public static Boolean revisarVariables(String codigo)
    {
        boolean salir=false;
        boolean resultado=true;
      
        while((codigo.substring(0,1).compareTo("D")==0)&&(!salir))
        {
            System.out.println(codigo.substring(0,codigo.indexOf(";")+1));
            if(revisarDeclaracionVariable(codigo.substring(0,codigo.indexOf(";")+1)))
            {
                codigo=codigo.substring(codigo.indexOf(";")+1);
                System.out.println("lo que queda:"+codigo);
                if(codigo.length()>0)
                {
                    codigo=codigo.substring(1);
                }
            }
            else{salir=true;resultado=false;}
        }
        return resultado;
    }
    public static String eliminarVariablesRevision(String codigo)
    {
        boolean salir=false;
        boolean resultado=true;
        while((codigo.substring(0,1).compareTo("D")==0)&&(!salir))
        {
            if(revisarDeclaracionVariable(codigo.substring(0,codigo.indexOf(";")+1)))
            {
                codigo=codigo.substring(codigo.indexOf(";")+1);
                if(codigo.length()>0)
                {
                    codigo=codigo.substring(1);
                }
            }
            else{salir=true;resultado=false;}
        }
        return codigo;
    }
    public static boolean verificarDentroDow(String codigo)
    {
        boolean resultado=true;
        String test=codigo.substring(codigo.indexOf("\n"),codigo.indexOf("Enddo;"));
        test=test.replaceAll("Exit", "");
        while ((codigo.compareTo("")!=0)&&(resultado))
        {
            if(cuerpoPrograma1(test).compareTo("error")==0)
            {
                resultado=false;
            }
            else
            {
                test=cuerpoPrograma1(test);
            }
        }
        return resultado;
    }
    public static boolean verificarDentroIf(String codigo)
    {
        boolean resultado=true;
        String test=codigo.substring(codigo.indexOf("\n"+1));
        boolean salir=false;
        while ((!salir)&&(resultado))
        {
            if(test.length()>6)
            {
                if(test.substring(0,6).compareTo("elseIf")==0)
                {
                    salir=true;
                }
            }
            else if(test.length()==0)
            {
                salir=true;
            }
            else if(cuerpoPrograma1(test).compareTo("error")==0)
                {
                    resultado=false;
                }
            else
            {
                test=cuerpoPrograma1(test);
            }
        }
        salir=false;
        while ((!salir)&&(resultado))
        {
            if(test.length()>4)
            {
                if(test.substring(0,4).compareTo("else")==0)
                {
                    salir=true;
                }
            }
            else if(test.length()==0)
            {
                salir=true;
            }
            else if(cuerpoPrograma1(test).compareTo("error")==0)
                {
                    resultado=false;
                }
            else
            {
                test=cuerpoPrograma1(test);
            }
        }
        salir=false;
        while ((!salir)&&(resultado))
        {
            if(test.length()>6)
            {
                if(test.substring(0,6).compareTo("Endif")==0)
                {
                    salir=true;
                }
            }
            else if(test.length()==0)
            {
                salir=true;
            }
            else if(cuerpoPrograma1(test).compareTo("error")==0)
                {
                    resultado=false;
                }
            else
            {
                test=cuerpoPrograma1(test);
            }
        }
        return resultado;
    }
    public static boolean verificarDentroFor(String codigo)
    {
        boolean resultado=true;
        String test=codigo.substring(codigo.indexOf("Times")+5,codigo.indexOf("FEnd"));
        boolean salir=false;
        while((!salir)&&(resultado))
        {
            if(test.compareTo("")==0)
            {
                salir=true;
            }
            else if(cuerpoPrograma1(test).compareTo("error")==0)
                {
                    resultado=false;
                }
            else
            {
                test=cuerpoPrograma1(test);
            }
        }
        return resultado;
    }
    public static String cuerpoPrograma1(String codigo)
    {
        if(codigo.substring(0,1).compareTo("D")==0)//ES dow
        {
            if(codigo.contains("Enddo;"))
            {
                if (revisarDow(codigo.substring(0, codigo.indexOf("Enddo;") + 6))) 
                {
                    if(verificarDentroDow(codigo.substring(0, codigo.indexOf("Enddo;") + 6)))
                    {
                        codigo = codigo.substring(codigo.indexOf("Enddo;") + 7);
                        if (codigo.length() > 0) 
                        {
                            codigo = codigo.substring(1);
                        }
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("F")==0)
        {
            if(codigo.contains("FEnd"))
            {
                if (revisarFor(codigo.substring(0, codigo.indexOf("FEnd") + 4))) 
                {
                    if(verificarDentroFor(codigo.substring(0, codigo.indexOf("FEnd") + 4)))
                    {
                        codigo = codigo.substring(codigo.indexOf("FEnd") + 4);
                        if (codigo.length() > 0) 
                        {
                            codigo = codigo.substring(1);
                        }
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("I")==0)//If o INZ
        {
            if(codigo.contains("Endif;"))
            {
                if (codigo.substring(0, 2).compareTo("If") == 0)//If ///////////////////////////////
                {
                    if(revisarIf(codigo.substring(0,codigo.indexOf("Endif")+6)))
                    {
                        if (codigo.substring(0, codigo.indexOf("Endif") + 6).contains("\n")) 
                        {
                            if (verificarDentroIf(codigo.substring(0, codigo.indexOf("Endif") + 6))) 
                            {
                                codigo = codigo.substring(codigo.indexOf("Endif") + 6);
                                if (codigo.length() > 0) 
                                {
                                    codigo = codigo.substring(1);
                                }
                            }
                        }
                    }
                }
            }
            else if(codigo.substring(0,3).compareTo("INZ")==0)//INZ
            {
                if(codigo.contains(";"))
                {
                    if (revisarIniciarVariables(codigo.substring(0, codigo.indexOf(";") + 1))) 
                    {
                        codigo = codigo.substring(codigo.indexOf(";") + 1);
                        if (codigo.length() > 0) 
                        {
                            codigo = codigo.substring(1);
                        }
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("+")==0)// Incremento
        {
            if(codigo.contains(";"))
            {
                if (revisarIncrementoDecremento(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("-")==0)// Decremento
        {
            if(codigo.contains(";"))
            {
                if (revisarIncrementoDecremento(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("T")==0)
        {
            if(codigo.contains(";"))
            {
                if (codigo.substring(0, codigo.indexOf(";") + 1).compareTo("TurnOn();") == 0) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                } 
                else if (codigo.substring(0, codigo.indexOf(";") + 1).compareTo("TurnOff();") == 0) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                } 
                else if (revisarTurnOn(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                } 
                else if (revisarTurnOff(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                }
            }
                
        }
        else if(codigo.substring(0,1).compareTo("S")==0)
        {
            if(codigo.contains(";"))
            {
                if (revisarSoundOn(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                } 
                else if (revisarSoundOff(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("C")==0)//revisar Call
        {
            if(codigo.contains(";"))
            {
                if (revisarCall(codigo.substring(0, codigo.indexOf(";") + 1))) 
                {
                    codigo = codigo.substring(codigo.indexOf(";") + 1);
                    if (codigo.length() > 0) 
                    {
                        codigo = codigo.substring(1);
                    }
                }
            }
        }
        else if(codigo.substring(0,1).compareTo("P")==0)// Procedimientos
        {
            if(revisarPs(codigo).compareTo("")==0)
            {
                codigo="";
            }
        }
        else{codigo="error";}
        return codigo;
    }
    public static String cuerpoPrograma(String codigo)
    {
        boolean salir=false;
        while(!salir)
        {
            if(cuerpoPrograma1(codigo).compareTo("error")==0)
            {
                salir=true;
            }
            else if(cuerpoPrograma1(codigo).compareTo("")==0)
            {
                salir=true;
            }
            else
            {
                codigo=cuerpoPrograma1(codigo);
            }
        }
        return codigo;
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
                    if(revisarVariables(codigo))
                    {
                        codigo=eliminarVariablesRevision(codigo);
                        if(codigo.contains(";"))//acordarme de revisar esto
                        {
                            
                        }
                    }
                    else{codigo=eliminarVariablesRevision(codigo);}
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

package javaapplication3;

public class JavaApplication3 {

    public static void main(String[] args) {
        String pruebaif = "If Vardaf = 233 \n"
                + "-Var1-;"
                + "\nelseIf Var = 32;"
                + "\n TurnOff(3,5,r);"
                + "\n TurnOff();"
                + "\n INZ Var3 = 342;"
                +"\nelse"
                + "\n Call Nal"
                + "\nEndif;"
                + "\nEndif;"
                + "\nTurnOff();";
        String pruebaDow="Dow Variable in var1 Step 2"
                + "\n-Var1-;"
                +"\nIf Vardaf = 233"
                + "\n-Var3-;"
                +"\nelse"
                +"\nExit"
                + "\nEndif;"
                +"\nEnddo;"
                + "\n-Var3-;";
        String prueba3="For 3 Times"
                + "\n-Var1-;"
                + "\n FEnd";
        //aplicaIf(prueba);
        
        System.out.println(aplicarCodigo(prueba3));
    }
    public static String eliminarComentario(String codigo) 
    {
        return codigo.substring(0, codigo.indexOf(">>"))
                + codigo.substring(codigo.indexOf(">>")).substring(codigo.indexOf("\n") + 2);
    }
    public static String eliminarComentarios(String codigo) 
    {
        while (codigo.contains(">>")) {
            codigo = eliminarComentario(codigo);
        }
        return codigo;
    }
    public static int indexEndif(String codigo)
    {
        int index = cantidadPrimeraLinea(codigo);
        int cantidadIf = 1;
        boolean salir = false;
        codigo=eliminarPrimeraLinea(codigo);
        while ((codigo.length() > 0) && (!salir)) 
        {
            if (primeraLinea(codigo).contains("If ")) 
            {
                cantidadIf++;
            } 
            else if (primeraLinea(codigo).contains("Endif;")) 
            {
                cantidadIf--;
            }
            if (cantidadIf == 0)
            {
                salir = true;
            } 
            else 
            {
                index += cantidadPrimeraLinea(codigo);
                codigo = eliminarPrimeraLinea(codigo);
            }

        }
        return index;
    }
    public static String primeraLinea(String codigo)
    {
        if (codigo.contains("\n")) {
            codigo = codigo.substring(0, codigo.indexOf("\n") + 1);
        }
        return codigo;
    }
    public static int cantidadPrimeraLinea(String codigo)
    {
        int cantidad = codigo.length();
        if (codigo.contains("\n")) {
            cantidad = codigo.substring(0, codigo.indexOf("\n") + 1).length();
        }
        return cantidad;
    }
    public static String eliminarPrimeraLinea(String codigo)
    {
        if (codigo.contains("\n")) {
            codigo = codigo.substring(codigo.indexOf("\n") + 1);
        } else {
            codigo = "";
        }
        return codigo;
    }
    public static String eliminarSaltoLinea(String codigo)
    {
        if(codigo.contains("\n"))
        {
            codigo=codigo.substring(0,codigo.indexOf("\n"));
        }
        return codigo;
    }
    public static String eliminarIf(String codigo)
    { 
        return eliminarPrimeraLinea(codigo.substring(indexEndif(codigo)));
    }
    public static String sacarCodigoDentroIf(String codigo)
    {
        return codigo.substring(cantidadPrimeraLinea(codigo),indexEndif(codigo));
    }
    public static String sacarElseIf(String codigo)
    {
        codigo=codigo.substring(codigo.indexOf("elseIf"));
        String test=eliminarPrimeraLinea(codigo);
        if(test.contains("elseIf"))
        {
            codigo=primeraLinea(codigo)+test.substring(0,test.indexOf("elseIf"));
        }
        else
        {
            codigo=primeraLinea(codigo)+test.substring(0,test.indexOf("else"));
        }
        return codigo;
    }
    public static String eliminarElseIf(String codigo)
    {
        codigo=codigo.substring(codigo.indexOf("elseIf"));
        String test=eliminarPrimeraLinea(codigo);
        if(test.contains("elseIf"))
        {
            codigo=test.substring(test.indexOf("elseIf"));
        }
        else
        {
            codigo=test.substring(test.indexOf("else"));
        }
        return codigo;
    }
    public static boolean existeVariableIf(String codigo)
    {
        boolean resultado=false;
        if(primeraLinea(codigo).contains("If "))
        {
            String nombreVariable=primeraLinea(codigo.replace(" ", "")).substring(2); 
            nombreVariable=nombreVariable.substring(0,nombreVariable.indexOf("="));
            resultado=true;
        }
        return resultado;
    }
    public static boolean valorVariableIf(String codigo)
    {
        String StringValor=primeraLinea(codigo).replace(" ","");
        StringValor=StringValor.substring(StringValor.indexOf("=")+1);
        StringValor=eliminarSaltoLinea(StringValor);
        int valorIf=Integer.parseInt(StringValor);
        return true;
    }
    public static boolean validarIf(String codigo)
    {
        boolean resultado=true;
        if(!primeraLinea(codigo).contains("else"))
        {
            if ((!existeVariableIf(codigo)) || (!valorVariableIf(codigo))) 
            {
                resultado = false;
            }
        }
        else{resultado=false;}
        return resultado;
    }
    public static boolean existeVariableElseIf(String codigo)
    {
        String nombreVariable=primeraLinea(codigo.replace(" ", "")).substring(6);
        nombreVariable=nombreVariable.substring(0,nombreVariable.indexOf("="));
        return true;
    }
    public static boolean valorVariableElseIf(String codigo)
    {
        String StringValor=primeraLinea(codigo).replace(" ","");
        StringValor=StringValor.substring(StringValor.indexOf("=")+1);
        StringValor=StringValor.substring(0,StringValor.indexOf(";"));
        int valorIf=Integer.parseInt(StringValor);
        return true;
    }
    public static boolean validarElseIf(String codigo)
    {
        boolean resultado=true;
        
            if ((!existeVariableElseIf(codigo)) || (!valorVariableElseIf(codigo))) 
            {
                resultado = false;
            }
        
        return resultado;
    }
    public static String aplicaIf(String codigo)
    {
        String resultado="";
        if(validarIf(codigo))
        {
            String dentroIf=sacarCodigoDentroIf(codigo);
            boolean salir=false;
            while((dentroIf.length()>0)&&(!salir))
            {
                dentroIf=aplicarLinea(dentroIf);
                if(dentroIf.contains("Problema con:"))
                {
                    salir=true;
                }
            }
            
            resultado=dentroIf;
        }
        else if((!existeVariableIf(codigo))&&(primeraLinea(codigo).contains("If ")))
        {
            String nombreVariable=primeraLinea(codigo.replace(" ", "")).substring(6);
            nombreVariable=nombreVariable.substring(0,nombreVariable.indexOf("="));
            resultado="No existe esta variable:"+nombreVariable;
        }
        else if(sacarCodigoDentroIf(codigo).contains("elseIf"))
        {
            codigo=aplicarElseIf(sacarCodigoDentroIf(codigo));
            codigo=resultado;
        }
        else// else
        {
            codigo=aplicarElse(sacarElse(codigo));
            codigo=resultado;
        }
        return resultado;
    }
    public static String sacarElse(String codigo)
    {
        codigo=codigo.substring(codigo.indexOf("else"));
        //codigo=codigo.substring(0,codigo.indexOf("Endif;"));
        return codigo;
    }
    public static boolean detectarErrores(String codigo)
    {
        boolean resultado=true;
        if((codigo.contains("No existe esta variable:"))||(codigo.contains("Problema con:")))
        {
            resultado=false;
        }
        return resultado;
    }
    public static String aplicarElseIf(String codigo)
    {
        codigo=codigo.substring(codigo.indexOf("elseIf"));
        boolean salir=false;
        while((!validarElseIf(codigo))&&(!primeraLinea(codigo).contains("else "))&&(!primeraLinea(codigo).contains("else\n")))
        {
            if(validarElseIf(codigo))
            {
                codigo=aplicarCodigo(eliminarPrimeraLinea(sacarElseIf(codigo)));
                salir=true;
            }
            else
            {
                codigo=eliminarElseIf(codigo);
            }
        }
        if(primeraLinea(codigo).contains("else ")||primeraLinea(codigo).contains("else\n"))
        {
            codigo=aplicarElse(codigo);
        }
        return codigo;
    }
    public static String aplicarElse(String codigo)
    {
        codigo=aplicarCodigo(eliminarPrimeraLinea(codigo));
        return codigo;
    }
    public static String aplicarDow(String codigo)
    {
        if(validarDow(codigo))
        {
            int valor1=1;//variable por aumentar con valor2
            int valor2=2;
            boolean salir=false;
            int cantidadMaximaDeIteraciones=100;
            while((!salir))
            {
                if(cantidadMaximaDeIteraciones<=0)
                {
                    System.out.println("Se ha superado la cantidad maxima de iteraciones");
                    codigo="Se ha superado la cantidad maxima de iteraciones";
                    salir=true;
                }
                else
                {
                    String test=aplicarCodigo(sacarDentroDow(codigo));
                    if(test.compareTo("")==0)
                    {
                        salir=true;
                    }
                    else if(!detectarErrores(test))
                    {
                        codigo=test;
                    }
                    else
                    {
                        cantidadMaximaDeIteraciones--;                        
                    }

                }
            }
        }
        return codigo;
    }
    public static String sacarDentroDow(String codigo)
    {
        codigo=eliminarPrimeraLinea(codigo);
        codigo=codigo.substring(0,indexEnddo(codigo));
        return codigo;
    }
    public static String eliminarDow(String codigo)
    {
        return eliminarPrimeraLinea(codigo.substring(indexEnddo(codigo)));
    }
    public static int indexEnddo(String codigo)
    {
        int index = cantidadPrimeraLinea(codigo);
        int cantidadIf = 1;
        boolean salir = false;
        codigo=eliminarPrimeraLinea(codigo);
        while ((codigo.length() > 0) && (!salir)) 
        {
            if (primeraLinea(codigo).contains("Dow ")) 
            {
                cantidadIf++;
            } 
            else if (primeraLinea(codigo).contains("Enddo;")) 
            {
                cantidadIf--;
            }
            if (cantidadIf == 0)
            {
                salir = true;
            } 
            else 
            {
                index += cantidadPrimeraLinea(codigo);
                codigo = eliminarPrimeraLinea(codigo);
            }

        }
        return index;
    }
    public static boolean validarDow(String codigo)
    {
        boolean resultado=false;
        if(primeraLinea(codigo).contains("Dow"))
        {
            codigo=codigo.replace(" ", "");
            String Var1=codigo.substring(codigo.indexOf("in")+2,codigo.indexOf("Step"));
            String Var2=codigo.substring(codigo.indexOf("Step")+4,codigo.indexOf("\n"));
            int valor=Integer.parseInt(Var2);
            System.out.println("Revisar Var:"+Var1+" y num:"+valor);
            resultado=true;
        }
        return resultado;
    }
    public static int nTimesFor(String codigo)
    {
        int ntimes=-1;
        if(codigo.contains("For "))
        {
            codigo=codigo.replace(" ", "");
            ntimes=Integer.parseInt(codigo.substring(3,codigo.indexOf("Times")));
        }
        return ntimes;
    }
    public static int indexFEnd(String codigo)
    {
        int index = cantidadPrimeraLinea(codigo);
        int cantidadIf = 1;
        boolean salir = false;
        codigo=eliminarPrimeraLinea(codigo);
        while ((codigo.length() > 0) && (!salir)) 
        {
            if (primeraLinea(codigo).contains("For ")) 
            {
                cantidadIf++;
            } 
            else if (primeraLinea(codigo).contains("FEnd")) 
            {
                cantidadIf--;
            }
            if (cantidadIf == 0)
            {
                salir = true;
            } 
            else 
            {
                index += cantidadPrimeraLinea(codigo);
                codigo = eliminarPrimeraLinea(codigo);
            }

        }
        return index;
    }
    public static String sacarDentroFor(String codigo)
    {
        codigo=eliminarPrimeraLinea(codigo);
        codigo=codigo.substring(0,indexFEnd(codigo));
        return codigo;
    }
    public static String eliminarFor(String codigo)
    {
        return eliminarPrimeraLinea(codigo.substring(indexFEnd(codigo)));
    }
    public static String aplicarFor(String codigo)
    {
        int nTimes=nTimesFor(codigo);
        for(int i=0;i<nTimes;i++)
        {
            String test=aplicarCodigo(sacarDentroFor(codigo));
            if(!detectarErrores(test))
            {
                codigo=test;
                break;
            }
        }
        return codigo;
    }
    public static String aplicarOneline(String codigo)
    {
        codigo=primeraLinea(codigo);
        if(codigo.startsWith("-"))
        {
            String nombreVar=codigo.substring(1);
            nombreVar=nombreVar.substring(0,nombreVar.indexOf("-"));
            System.out.println("Decrementar esta variable:"+nombreVar);
            //MandarDecremento
            codigo="";
        }
        else if(codigo.startsWith("+"))
        {
            String nombreVar=codigo.substring(1);
            nombreVar=nombreVar.substring(0,nombreVar.indexOf("+"));
            System.out.println("Aumentar esta variable:"+nombreVar);
            //MandarIncremento
            codigo="";
        }
        else if(codigo.contains("INZ "))
        {
            String nombreVar=codigo.replace(" ", "");
            nombreVar=nombreVar.substring(3,nombreVar.indexOf("="));
            String valorMod=codigo.replace(" ", "");
            valorMod=valorMod.substring(valorMod.indexOf("=")+1,valorMod.indexOf(";"));
            int valor=Integer.parseInt(valorMod);
            System.out.println("Modificar esta variable:"+nombreVar+" con:"+valor);
            codigo="";
        }
        else if(codigo.contains("TurnOn("))
        {
            String numCol=codigo.substring(7,8);
            String numFil=codigo.substring(9,10);
            String color=codigo.substring(11,12);
            System.out.println("Prender led en col:"+numCol+" fil:"+numFil+" con el color:"+color);
            codigo="";
        }
        else if(codigo.contains("TurnOff();"))
        {
            System.out.println("Apague todos los leds");
            codigo="";
        }
        else if(codigo.contains("TurnOff("))
        {
            String numCol=codigo.substring(9,10);
            String numFil=codigo.substring(11,12);
            String color=codigo.substring(13,14);
            System.out.println("Apagar led en col:"+numCol+" fil:"+numFil+" con el color:"+color);
            codigo="";
        }
        else if(codigo.contains("TurnON();"))
        {
            System.out.println("Encienda todos los leds");
            codigo="";
        }
        else if(codigo.contains("SoundOff();"))
        {
             System.out.println("Apague el sonido");
             codigo="";
        }
        else if(codigo.contains("SoundOn("))
        {
            String parametro=codigo.substring(codigo.indexOf("(")+1,codigo.indexOf(")"));
            System.out.println("Sonido:"+parametro);
            codigo="";
        }
        else if(codigo.contains("Call "))
        {
            String nombreParametro=codigo.replace("Call", "");
            nombreParametro=nombreParametro.replace(" ","");
            nombreParametro=nombreParametro.replace("\n", "");
            System.out.println("Verificar si existe este procedimiento:"+nombreParametro);
            codigo="";
        }
        return codigo;
    }
    public static String aplicarCodigo(String codigo)
    {
        boolean salir=false;
        while((!salir)&&(detectarErrores(codigo))&&(codigo.length()>0))
        {
            if(!detectarErrores(codigo))
            {
                salir=true;
            }
            else if(primeraLinea(codigo).contains("Exit"))
            {
                codigo="";
                salir=true;
            }
            else
            {
                codigo=aplicarLinea(codigo);
            }
        }
        return codigo;
    }
    public static String aplicarLinea(String codigo)
    {
        if((primeraLinea(codigo).contains("If "))||(primeraLinea(codigo).contains("else")))
        {
            String test=aplicaIf(codigo);
            if (detectarErrores(test))
            {
                codigo=eliminarIf(codigo);
            }
            else
            {
                codigo=test;
            }
        }
        else if((primeraLinea(codigo).contains("Dow")))
        {
            String test=aplicarDow(codigo);
            if (detectarErrores(test))
            {
                codigo=eliminarDow(codigo);
            }
            else
            {
                codigo=test;
            }
        }
        else if((primeraLinea(codigo).contains("For ")))
        {
            String test=aplicarFor(codigo);
            if(detectarErrores(test))
            {
                codigo=eliminarFor(codigo);
            }
            else
            {
                codigo=test;
            }
        }
        else
        {
            String test=aplicarOneline(codigo);
            if(primeraLinea(codigo).compareTo(test)==0)
            {
                if(test.contains("Exit"))
                {
                    codigo=test;
                }
                else
                {
                    codigo="Problema con:"+test;
                }
            }
            else
            {
                codigo=eliminarPrimeraLinea(codigo);
            }
        }
        return codigo;
    }
}

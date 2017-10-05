
public class listaProcedimietnos 
{

	public static void main(String[] args) 
	{
		String prueba="P Hola324 B\n"
				+ "x\n"
				+ "P Hola324 E";
		System.out.println(verificarNombresProcedimiento(prueba));
	}
	public static boolean verificarNombreProcedimiento(String nombre)
	{
		return nombre.matches("[A-Z][A-Za-z0-9]*");
	}
	public static boolean verificarAbrirProcedimiento1(String codigo)
	{
		boolean resultado=(codigo.substring(0,2).matches("P "))&&
				(verificarNombreProcedimiento(codigo.substring(2,codigo.length()-2)))&&
				(codigo.substring(codigo.length()-2)).matches(" B");
		return resultado;
	}
	public static boolean verificarAbrirProcedimiento(String codigo)
	{
		return verificarAbrirProcedimiento1(primeraLinea(codigo));
	}
	public static boolean verificarCerrarProcedimiento1(String codigo)
	{
		boolean resultado=(codigo.substring(0,2).matches("P "))&&
				(verificarNombreProcedimiento(codigo.substring(2,codigo.length()-2)))&&
				(codigo.substring(codigo.length()-2)).matches(" E");
		return resultado;
	}
	public static boolean verificarCerrarProcedimiento(String codigo)
	{
		return verificarCerrarProcedimiento1(ultimaLinea(codigo));
	}
	public static String eliminarPrimeraLinea(String codigo)
	{
		if(codigo.contains("\n"))
		{
			codigo=codigo.substring(codigo.indexOf("\n")+1);
		}
		else
		{
			codigo="";
		}
		return codigo;
	}
	public static String primeraLinea(String codigo)
	{
		if(codigo.contains("\n")) 
		{
			codigo=codigo.substring(0,codigo.indexOf("\n"));
		}
		return codigo;
	}
	public static String ultimaLinea(String codigo)
	{
		while(codigo.contains("\n"))
		{
			codigo=eliminarPrimeraLinea(codigo);
		}
		return codigo;
	}
	public static boolean verificarNombresProcedimiento(String codigo)
	{
		boolean resultado=false;
		String nombre1="";
		String nombre2="";
		while((codigo.length()>0)&&(!resultado))
		{
			if(primeraLinea(codigo).contains("P "))
			{
				System.out.println(primeraLinea(codigo).substring(primeraLinea(codigo).length()-2).matches(" B"));
				if(primeraLinea(codigo).substring(primeraLinea(codigo).length()-2).matches(" B"))
				{
					System.out.println(primeraLinea(codigo).substring(2,codigo.length()-2));
					nombre1=primeraLinea(codigo).substring(2,codigo.length()-2);
				}
				else if(primeraLinea(codigo).substring(primeraLinea(codigo).length()-2).matches(" E"))
				{
					nombre2=codigo.substring(2,codigo.length()-2);
				}
				else
				{
					codigo="";
				}
			}
			else if(nombre1.compareTo(nombre2)==0)
			{
				resultado=true;
			}
			else
			{
				codigo=eliminarPrimeraLinea(codigo);
			}
		}
		return resultado;
	}
	public static boolean verificarEstructuraBasicaProcedimiento(String codigo)
	{
		boolean resultado=false;
		if(codigo.length()>3)
		{
			System.out.println(verificarCerrarProcedimiento(codigo));
			resultado=verificarAbrirProcedimiento(codigo)&&
					verificarCerrarProcedimiento(codigo)&&
					verificarNombresProcedimiento(codigo);
		}
		return resultado;
	}
}

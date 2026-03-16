import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Rover {
  
  private String nombre;
  private double cantidadPotenciaInicial = 100.0;
  private double cantidadPotencia;
  private int posicionX;
  private int posicionY;
  private int posicionInicialX = 0;
  private int posicionInicialY = 0;
  private String codigoUnico;
  private int deteccionesCalor = 0;
  private ArrayList<ArrayList<String>> mandatosExitosos;
  private ArrayList<ArrayList<String>> mandatosFallidos;
  private int cantidadRecargas;
  
  private static int contador = 0;
  private static String informacionRovers = "";
  
  
  public Rover(String pNombre, double pCantidadPotencia) {
    this.nombre = pNombre;
    this.cantidadPotenciaInicial = pCantidadPotencia;
    this.cantidadPotencia = cantidadPotenciaInicial;
    this.posicionX = posicionInicialX;
    this.posicionY = posicionInicialY;
    contador += 1;
    this.codigoUnico = "RVR-" + contador;
    this.mandatosExitosos = new ArrayList<>();
    this.mandatosFallidos = new ArrayList<>();
    this.cantidadRecargas = 0;
    
    // registrar la informacion del Rover creado
    informacionRovers += "\nRover creado: " +
                         "\nCodigo: " + codigoUnico +
                         "\nNombre: " + nombre +
                         "\nPotencia inicial: " + cantidadPotenciaInicial +
                         "\nPotencia disponible: " + cantidadPotencia +
                         "\nRecargas realizadas: " + cantidadRecargas +
                         "\nDetecciones de calor: " + deteccionesCalor +
                         "\nPosicion inicial: (" + posicionInicialX + "," + posicionInicialY + ")" +
                         "\nPosicion actual: (" + posicionX + "," + posicionY + ")" +
                         "\n";
  }
            
  
  public boolean validarDireccionDesplazamiento(String pDireccion) {
    if (cantidadPotencia < 0.25) return false;
    cantidadPotencia -= 0.25;
    deteccionesCalor += 1;
    double valor = Math.random();
    return valor < 0.5; 
  }

    
  public void desplazar(String pDireccion, int pDesplazamientos) {
    if (!verificarPotencia(pDesplazamientos)) {
      registrarMandato("Desplazar " + pDireccion, "No posible");
      return;
    }

    if (!validarDireccionDesplazamiento(pDireccion)) {
      registrarMandato("Desplazar " + pDireccion, "No posible");
      return;
    }

    switch (pDireccion.toLowerCase()) {
      case "adelante": posicionY += pDesplazamientos; break;
      case "atras": posicionY -= pDesplazamientos; break;
      case "derecha": posicionX += pDesplazamientos; break;
      case "izquierda": posicionX -= pDesplazamientos; break;
      default:
        registrarMandato("Desplazar " + pDireccion, "No posible");
        return;
    }

    consumirPotencia(pDesplazamientos);

    registrarMandato("Desplazar " + pDireccion, "Ejecutado");
  }
  
  
  public void conocerPosicion() {
    System.out.println("Posición actual: (" + posicionX + "," + posicionY + ")");
  }
                         

  public void conocerPotencia() {
    System.out.println("Potencia disponible: " + cantidadPotencia);
  }

  
  public void consumirPotencia(int pDesplazamientos) {
    cantidadPotencia -= (0.5 * pDesplazamientos);
  }
  
  
  public boolean verificarPotencia(int pDesplazamientos) {
    return cantidadPotencia >= (0.5 * pDesplazamientos);
  }
  
  
  public void conocerEstado() {
    System.out.println("Estado del Rover: ");
    System.out.println("Codigo: " + codigoUnico);
    System.out.println("Nombre: " + nombre);
    System.out.println("Potencia inicial: " + cantidadPotenciaInicial);
    System.out.println("Potencia disponible: " + cantidadPotencia);
    System.out.println("Recargas realizadas: " + cantidadRecargas);
    System.out.println("Detecciones de calor: " + deteccionesCalor);
    System.out.println("Posicion inicial: (" + posicionInicialX + "," + posicionInicialY + ")");
    System.out.println("Posicion actual: (" + posicionX + "," + posicionY + ")");
    
    System.out.println("Mandatos exitosos: \n"); 
    listarMandatos(mandatosExitosos);
    System.out.println("Mandatos fallidos: \n"); 
    listarMandatos(mandatosFallidos);
    
    System.out.println("\n");
  }
  
  
  public void registrarMandato(String pTipoMandato, String pEstatusMandato) {
    ArrayList<String> mandato = new ArrayList<>();
    mandato.add(determinarFechaYHoraActual()); 
    mandato.add(pTipoMandato);                
    mandato.add(pEstatusMandato);              

    if (pEstatusMandato.equals("Ejecutado")) {
        mandatosExitosos.add(mandato);
    } else {
        mandatosFallidos.add(mandato);
    }
  }


  public String determinarFechaYHoraActual() {
    LocalDateTime ahora = LocalDateTime.now();
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
    return "Fecha: " + ahora.format(formatoFecha) + " | Hora: " + ahora.format(formatoHora);
  }
  
  
  public void listarMandatos(ArrayList<ArrayList<String>> pMandatos) {
    if (pMandatos.isEmpty()) {
      System.out.println("No hay mandatos registrados ");
    } else {
      for (ArrayList<String> mandato : pMandatos) {
        System.out.println("Mandato: " + mandato.get(0) + " | Tipo: " + mandato.get(1) + " | Estatus: " + mandato.get(2) + "\n");
      }
    }
  }

  
  public void recargarPotencia(double pUnidadesARecargar) {
    if (cantidadRecargas >= 5) {
      registrarMandato("Recargar potencia", "No posible");
      System.out.println("Accion no valida, se alzanco el limite de recargas");
      return;
    }
    
    cantidadPotencia += pUnidadesARecargar;
    cantidadRecargas += 1;
    System.out.println(" Se ha cargado " + pUnidadesARecargar + " unidades");
    registrarMandato("Recargar potencia", "Ejecutado");
  }

  public void consultarCantidadRoversCreados() {
    System.out.println("Se han creado " + contador + " rovers" );
  }
  
  
  public static void listarInformacionRoversCreados() {
    if (informacionRovers.isEmpty()) {
      System.out.println("Accion no valida, no se han creado Rovers aun");
    } else {
      System.out.println("Informacion de Rovers creados:");
      System.out.println(informacionRovers);
    }
  }

  
} 

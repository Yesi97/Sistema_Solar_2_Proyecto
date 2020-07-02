package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Sistema_Solar_2_Proyecto Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Sistema_Solar_2_Proyecto extends SimpleApplication 
{ //inicio

    public static void main(String[] args) 
    {
        Sistema_Solar_2_Proyecto app = new Sistema_Solar_2_Proyecto();
        app.start();
    }

    //variables para los angulos de los planetas
    
    public double anguloMercurio, anguloVenus, anguloTierra, anguloLuna = 0;
   
     
     /**
         * Para poder visualizar un objeto en jMonkey se necesita definir un objeto Geometry, 
         * el cual requiere los vértices y aristas del objeto tridimensional, así como el 
         * material de este.
         */
        
        /*Esfera para el sol*/
        
        Sphere sol = new Sphere(32, 32, 7f);
        Geometry geomSol = new Geometry("Sol", sol);
        
         /**
         * Ya que utilizaremos una figura geométrica básica, la “esfera”, el motor proporción 
         * una clase la cual sólo debemos revisar en el API, para identificar cada parámetro de 
         * su constructor. En este caso, para la clase Sphere(int zSample, int radialSamples, 
         * float radius), zSample – El número de muestras a lo largo del eje z, 
         * radialSample -  El número de muestras a lo largo del radial, radius – El radio de la esfera.
         * 
         **/
        
        /* Creamos 4 esferas: mercurio, venus, tierra y la luna*/
        
        Sphere mercurio = new Sphere(32, 32, 2f);
        Geometry geomMercurio = new Geometry("Mercurio", mercurio);
        
        Sphere venus = new Sphere(32, 32, 4f);
        Geometry geomVenus = new Geometry("Venus", venus);
        
        Sphere tierra = new Sphere(32, 32, 4f);
        Geometry geomTierra = new Geometry("Tierra", tierra);
        
        Sphere luna = new Sphere(32, 32, 1f);
        Geometry geomLuna = new Geometry("Luna", luna);
        
        // Se crea un objeto llamado "camera", para el Vector3f
        // Esto es para posicionar la camara en el espacio a cierto numero de frames
        Vector3f camera = new Vector3f(0f, 0f, 125f);
        
    @Override
    public void simpleInitApp() 
    {
      
        /**
         * Los objetos Node podrías imaginarlos como cajas que contendrán otras cajas o modelos (esferas, 
         * personaje, etc.). Al igual que si mueves una caja, se mueve todo lo de su interior, si rotas 
         * o trasladas un Node, aplica lo mismo a los objetos que contiene. 
         **/
        Node pivotMercurio = new Node("pivote_mercurio");
        Node pivotVenus = new Node("pivote_venus");
        Node pivotTierra = new Node("pivote_tierra");
        
        Node nodeSol = new Node("nodo_sol");
        Node nodeMercurio = new Node("nodo_mercurio");
        Node nodeVenus = new Node("nodo_venus");
        Node nodeTierra = new Node("nodo_tierra");
        
        // Estas instrucciones son para posicionar la camara
        // y que no se mueva cuando uno pase el mouse, a menos de que 
        // los selecciones y los muevas
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        
      
        cam.setLocation(camera);
        
         /**
         * Por el momento utilizaremos materiales que en si son simples colores.  
         * Mas adelante tendrán otra apariencia.
         **/
         
         /*Materiales para cada una de nuestras esferas (sol, planetas y la luna)*/
         
        Material matSol = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material matMercurio = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material matVenus = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material matTierra = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material matLuna = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        matSol.setColor("Color", ColorRGBA.Yellow);
        matMercurio.setColor("Color", ColorRGBA.Brown);
        matVenus.setColor("Color", ColorRGBA.Orange);
        matTierra.setColor("Color", ColorRGBA.Blue);
        matLuna.setColor("Color", ColorRGBA.White);
        
        geomSol.setMaterial(matSol);
        geomMercurio.setMaterial(matMercurio);
        geomVenus.setMaterial(matVenus);
        geomTierra.setMaterial(matTierra);
        geomLuna.setMaterial(matLuna);
        
        geomLuna.setLocalTranslation(3f, 0f, 0f);
        
        /**
         * Los Nodes son un medio para agrupar otros nodos y/o objetos del tipo Geometry. Además, 
         * se utilizan comúnmente para aplicar transformaciones a los spatials que agrupan.
         * 
         * A través del comando “attachChild( Geometry)” estaremos organizando lo que cada 
         * Node tendrá, esto lo hacemos de acuerdo a una idea que definimos previamente, la 
         * cual nos permitirá generar la ilusión de rotación en forma de orbita para cada uno de 
         * nuestros planetas
         */
        
        nodeTierra.attachChild(geomTierra);
        nodeTierra.attachChild(geomLuna);
        
        nodeSol.attachChild(geomSol);
        
        nodeMercurio.attachChild(geomMercurio);
        nodeVenus.attachChild(geomVenus);
        
        nodeMercurio.setLocalTranslation(2f, 0, 0f);
        nodeVenus.setLocalTranslation(4f, 0, 0f);
        nodeTierra.setLocalTranslation(6f, 0, 0f);
        
        /*Estos pivotes serán de ayuda para asignar la velocidad que tendrá cada uno*/
        
        pivotTierra.attachChild(nodeSol);
        pivotMercurio.attachChild(nodeMercurio);
        pivotVenus.attachChild(nodeVenus);
        pivotTierra.attachChild(nodeTierra);
        
       /**
         * Recuerda, para que se pueda visualizar algún Spatial(Node o Geometry) en el escenario, 
         * necesita estar adjuntado al “rootNode”. Además, ya que se genera un relación de padre – hijo, 
         * si agregas al padre por lo tanto también agregas el hijo, regresando a la visualización de cajas, 
         * si agregas una caja, por transitividad, también lo que este adentro estará agregado. 
        **/
       
        rootNode.attachChild(pivotMercurio);
        rootNode.attachChild(pivotVenus);
        rootNode.attachChild(pivotTierra);
        rootNode.attachChild(geomLuna);
       
        
    } //final

    
    @Override
    public void simpleUpdate(float tpf) 
    {
        //al inicio se declararon unas variables publicas con caracterisitca 
        //double, estas entran en juego en este momento ya que se les asingará un valor
        
        anguloMercurio = anguloMercurio + 0.06;
        anguloVenus = anguloVenus + 0.04;
        anguloTierra = anguloTierra + 0.02;
        anguloLuna = anguloTierra + 0.08;
        
        
        float r = FastMath.DEG_TO_RAD;
        double Mercurio = FastMath.DEG_TO_RAD*anguloMercurio;
        double Venus = FastMath.DEG_TO_RAD*anguloVenus;
        double Tierra = FastMath.DEG_TO_RAD*anguloTierra;
        double Luna = FastMath.DEG_TO_RAD*anguloLuna;
        
        //Posición de los planetas en el espacio
        
        int rMer = 12;
        int rVenus = 20;
        int rTierra = 32;
        int rLuna = 38;
        
        // Coordenadas para hacer que los planetas giren alrededor del sol
        
        float xMer = (float) Math.sin(Mercurio)*rMer;
        float yMer = (float) Math.cos(Mercurio)*rMer;
        
        float xVenus = (float) Math.sin(Venus)*rVenus;
        float yVenus = (float) Math.cos(Venus)*rVenus;
        
        float xTierra = (float) Math.sin(Tierra)*rTierra;
        float yTierra = (float) Math.cos(Tierra)*rTierra;
        
        float xLuna = (float) Math.sin(Luna)*rLuna;
        float yLuna = (float) Math.cos(Luna)*rLuna;
        
        geomSol.rotate(r,0f,0f);
        geomMercurio.rotate(r,0f,0f);
        geomVenus.rotate(r,0f,0f);
        geomTierra.rotate(r,0f,0f);
        geomLuna.rotate(r, 0f, 0f);
        
        //Rotación alrededor del sol
        
        Vector3f orbMercurio = new Vector3f (xMer , yMer, 0);
        Vector3f orbVenus = new Vector3f (xVenus , yVenus, 0);
        Vector3f orbTierra = new Vector3f (xTierra , yTierra, 0);
        Vector3f orbLuna = new Vector3f (xLuna, yLuna, 0);
        
        
        geomMercurio.setLocalTranslation(orbMercurio);
        geomVenus.setLocalTranslation(orbVenus);
        geomTierra.setLocalTranslation(orbTierra);
        geomLuna.setLocalTranslation(orbLuna);
        
        //TODO: add update code
    } // final

    
    @Override
    public void simpleRender(RenderManager rm) 
    {
        //TODO: add render code
    } // final
    
} // final

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Main {

    public static class ModifierMethodWriter extends MethodVisitor implements Opcodes{
        private String methodName;
        private String className;
        public ModifierMethodWriter(int api, MethodVisitor mv, String methodName, String className) {
            super(api, mv);
            this.methodName=methodName;
            this.className = className;
        }

        @Override
        public void visitCode() {

            super.visitCode();
            super.visitLdcInsn("method: "+methodName+" "+className);
            super.visitMethodInsn(INVOKESTATIC, "Compteur",  "compte", "(Ljava/lang/String;)V");          
        }    
    }

    
    public static class ModifierClassWriter extends ClassVisitor{
        private int api;
        String t;
        public ModifierClassWriter(int api, ClassWriter cv, String t) {
            super(api, cv);
            this.t = t;
            this.api=api;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
            String signature, String[] exceptions) {

            MethodVisitor mv= super.visitMethod(access, name, desc, signature, exceptions);
            ModifierMethodWriter mvw=new ModifierMethodWriter(api, mv, name, t);
            return mvw;
        }
    }

    public static void main(String[] args) throws IOException {
 
    	String chemin_jar;
    	Scanner saisieUtilisateur = new Scanner(System.in);
    	System.out.println("Veuillez saisir le chemin d'un jar :");
    	chemin_jar = saisieUtilisateur.next();
    	System.out.println("Veuillez saisir le package :");
    	String nom_package = saisieUtilisateur.next(); 
    	saisieUtilisateur.close();

    	nom_package = nom_package.replace(".", "/");

    	JarFile jf = new JarFile(chemin_jar);
        Enumeration<JarEntry> ee =  jf.entries();

        JarEntry je = null;
        List<String> l = new ArrayList<String>();
        
        while(ee.hasMoreElements()){
        	je = ee.nextElement();
        	if(je.toString().contains(nom_package)){
        		l.add(je.toString());
        	}
        }
        ZipEntry ze;
//home/Soge/Bureau/test.jar
        for(int i=0;i<l.size();i++){
        	String chemin = l.get(i);
        	
            ze = jf.getEntry(chemin);
            InputStream in = jf.getInputStream(ze);
     
            ClassReader classReader=new ClassReader(in);
            
            ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);            
            ModifierClassWriter mcw=new ModifierClassWriter(Opcodes.ASM4, cw, chemin.substring(chemin.lastIndexOf("/")));
            classReader.accept(mcw, 0);       
           
            File outputDir=new File("out/"+l.get(i).substring(0, chemin.lastIndexOf("/")+1));
            outputDir.mkdirs();
            DataOutputStream dout=new DataOutputStream(new FileOutputStream(new File(outputDir,chemin.substring(chemin.lastIndexOf("/")))));
            dout.write(cw.toByteArray());
            dout.close();
            in.close();
        }
        jf.close();
       
    }
 
       
    

}
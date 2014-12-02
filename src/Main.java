import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
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

        JarFile jf = new JarFile("/home/Soge/Téléchargements/asm-4.0.jar");
       Enumeration<JarEntry> ee =  jf.entries();
       JarEntry je = ee.nextElement();
       while(ee.hasMoreElements()){
    	   System.out.println(je);
    	   je=ee.nextElement();    	   System.out.println(je);

    	   
       }
        ZipEntry ze = jf.getEntry("org/objectweb/asm/ClassReader.class" );
        System.out.println(ze);
        InputStream inn = jf.getInputStream(ze);
    	
        File rep = new File("bin/");
        String[] liste = rep.list();

        for(int i=0;i<liste.length;i++){
        	System.out.println(liste[i]);
        	if(liste[i].endsWith(".class")==true && !liste[i].contains("Compteur") && !liste[i].contains("Main")){
        		InputStream in=Main.class.getResourceAsStream("../bin/"+liste[i]);
                ClassReader classReader=new ClassReader(in);
                ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                ModifierClassWriter mcw=new ModifierClassWriter(Opcodes.ASM4, cw, liste[i]);
                classReader.accept(mcw, 0);       
                
                File outputDir=new File("bin/");
                outputDir.mkdirs();
                DataOutputStream dout=new DataOutputStream(new FileOutputStream(new File(outputDir,liste[i])));
                dout.write(cw.toByteArray());
        	}
        } 
       
    }

}
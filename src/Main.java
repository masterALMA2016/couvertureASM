/*
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class Main {

    public static class MethodPrinterVisitor extends ClassVisitor{

        public MethodPrinterVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        public MethodPrinterVisitor(int api) {
            super(api);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                String signature, String[] exceptions) {
            
            //System.out.println("\n"+name+desc);
            
            MethodVisitor oriMv= new MethodVisitor(Opcodes.ASM4) {
            };
            //An instructionAdapter is a special MethodVisitor that
            //lets us process instructions easily
            InstructionAdapter instMv=new InstructionAdapter(oriMv){

                @Override
                public void visitInsn(int opcode) {
                    System.out.println(opcode);
                    super.visitInsn(opcode);
                }
                
            };
            return instMv;
            
        }
               

    }
    
    
    public static void main(String[] args) throws Exception{
        InputStream in=Main.class.getResourceAsStream("/java/lang/String.class");
        ClassReader classReader=new ClassReader(in);
        MethodPrinterVisitor mp = new MethodPrinterVisitor(Opcodes.ASM4);
        classReader.accept(mp, 0);

    }

}
*/

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Main {

    public static class ModifierMethodWriter extends MethodVisitor implements Opcodes{

        private String methodName;
        
        public ModifierMethodWriter(int api, MethodVisitor mv, String methodName) {
            super(api, mv);
            this.methodName=methodName;
        }

        //This is the point we insert the code. Note that the instructions are added right after
        //the visitCode method of the super class. This ordering is very important.
        @Override
        public void visitCode() {
        	
        	//System.out.println("je suis allé dans "+ methodName);
            super.visitCode();
            
            //System.out.println("method: "+methodName);
            /*
            super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            super.visitLdcInsn("method: "+methodName);
            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            */
            System.out.println(methodName);
           //         super.visitLdcInsn("method: "+methodName);

            //super.visitMethodInsn(INVOKESTATIC, "Compteur",  "getInstance", "(Ljava/lang/String;)V");
            //super.visitInsn(POP);
            super.visitLdcInsn("method: "+methodName);
            super.visitMethodInsn(INVOKESTATIC, "Compteur",  "compte", "(Ljava/lang/String;)V");
            	
            //un probleme avec ca
           //super.visitMethodInsn(Opcodes.INVOKESTATIC, "Compteur", "compte", "(Lvoid;)V");
           /*
            * on veut rajouter ca dans le bit code :
					 			
					Comteur.getInstance();
					Compteur.compte();
					
				Ce qui doit donner :
				
				 invokestatic Compteur.getInstance() : Compteur [56]
    			 pop
    			 ldc <String "method: main"> [58]
    			 invokestatic Compteur.compte(java.lang.String) : void [60]
    			 
    			 mais on faisait ca:
    			 
    			  getstatic Compteur.getInstance : Compteur [17]
     			  ldc <String "method: main"> [53]
      			  invokestatic Compteur.compte(void) : void [23]
            * 
            * 
            */
            
        }

        
        
    }
    
    //Our class modifier class visitor. It delegate all calls to the super class
    //Only makes sure that it returns our MethodVisitor for every method
    public static class ModifierClassWriter extends ClassVisitor{
        private int api;
        public ModifierClassWriter(int api, ClassWriter cv) {
            super(api, cv);
            this.api=api;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                String signature, String[] exceptions) {

            MethodVisitor mv= super.visitMethod(access, name, desc, signature, exceptions);
            ModifierMethodWriter mvw=new ModifierMethodWriter(api, mv, name);
            return mvw;
        }
        
        
        
    }

    public static void main(String[] args) throws IOException {
        InputStream in=Main.class.getResourceAsStream("../bin/TestClass.class");
        ClassReader classReader=new ClassReader(in);
        ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        
        //Wrap the ClassWriter with our custom ClassVisitor
        ModifierClassWriter mcw=new ModifierClassWriter(Opcodes.ASM4, cw);
        classReader.accept(mcw, 0);       
        
        //Write the output to a class file
        File outputDir=new File("out/");
        outputDir.mkdirs();
        DataOutputStream dout=new DataOutputStream(new FileOutputStream(new File(outputDir,"TestClass.class")));
        dout.write(cw.toByteArray());
    }

}
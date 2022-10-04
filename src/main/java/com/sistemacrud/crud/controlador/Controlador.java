package com.sistemacrud.crud.controlador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sistemacrud.crud.InterfaceService.InterfacePostreService;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUpload;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload; 
 
//import org.apache.commons.fileupload.FileUploadException;

import com.sistemacrud.crud.Model.Postre;

@Controller
@RequestMapping
public class Controlador {

	@Autowired
	private InterfacePostreService service;
	
	//Directorio donde se subiran las imagenes
	public static String UPLOAD = "src/main/resourses/static/uploads/";
	
	//LISTAR REGISTROS
	public String listar(Model model) {
		//SELECCIONAMOS EL MODELO POSTRES Y LISTAMOS LOS REGISTROS DESDE LA DB
		List<Postre> postres = service.listar();
		model.addAttribute("postres", postres);
		
		return "index";
	}
	
	//CREAR REGISTRO
	@GetMapping("/crear")
	public String crear (Model model) {
		//CARGAMOS EL MODELO POSTRE Y MOSTRAMOS UN FORMULARIO PARA CREAR UN NUEVO REGISTRO
		model.addAttribute("postres", new Postre());
		return "crear";
	}
	
	//GUARDAR LOS DATOS EN LA DB Y SUBIR LA IMAGEN AL SERVIDOR
	@PostMapping
	public String guardar (@Validated Postre p, Model model, @RequestParam("img") MultipartFile multipartFile) throws IOException {
		//OBTENEMOS EL NOMBRE DE LA IMAGEN
		String nombreImagen = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		p.setImg(nombreImagen);
		
		//SUBIMOS LA IMAGEN AL SERVIDOR
		byte[] bytes = multipartFile.getBytes();
		Path path = Paths.get(UPLOAD + multipartFile.getOriginalFilename());
		Files.write(path,  bytes);
		
		//GUARDAMOS LOS DATOS DEL FORMULARIO EN LA DB
		this.service.save(p);
		
		//LUEGO DE REALIZAR LAS TAREAS CORRESPONDIENTES, REDIRECCIONAMOS A LA VISTA PRINCIPAL
		return "redirect:/";
	}
	
	
	// ACTUALIZAR UN REGISTRO
	@GetMapping("/actualizar/{id}")
	public String actualizar(@PathVariable Long id, Model model){
	 
	    // Recibimos el 'id' del registro que se va Actualizar 
	    Optional<Postre> postres = this.service.getId(id);
	 
	    // Guardamos los datos del formulario en la base de datos 
	    model.addAttribute("postres", postres);
	 
	    // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
	    return "actualizar";
	 
	}
	
	
	// ELIMINAR UN REGISTRO 
	@GetMapping("/eliminar/{id}") 
	public String delete(@PathVariable long id, Model model){
	 
	    // Recibimos el 'id' del registro a eliminar  
	    this.service.delete(id);
	 
	    // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
	    return "redirect:/";
	 
	}
	
}

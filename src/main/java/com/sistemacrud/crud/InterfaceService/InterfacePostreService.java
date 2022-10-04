package com.sistemacrud.crud.InterfaceService;

import java.util.List;
import java.util.Optional;

import com.sistemacrud.crud.Model.Postre;

public interface InterfacePostreService {

	//METODOS
	public List<Postre> listar();
	public Optional<Postre> getId(Long id);
	public int save(Postre p);
	public void delete(Long id);
}

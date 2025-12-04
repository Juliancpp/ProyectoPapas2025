package com.example.DAO;

import java.util.List;



public interface IGenericDAO<T, K> {

    void insertar(T entidad) throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(K clave) throws Exception;

    T obtenerPorId(K clave) throws Exception;

    List<T> obtenerTodos() throws Exception;

    boolean existe(K clave) throws Exception;

    long contar() throws Exception;

    void insertarBatch(List<T> entidades) throws Exception;

    void eliminarBatch(List<K> claves) throws Exception;

    List<T> buscarPorCampo(String campo, Object valor) throws Exception;

    List<T> buscarLike(String campo, String patron) throws Exception;

    List<T> obtenerPagina(int offset, int limite) throws Exception;

}

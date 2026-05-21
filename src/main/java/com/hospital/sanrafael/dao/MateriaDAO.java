package com.hospital.sanrafael.dao;

import com.hospital.sanrafael.model.Materia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {
    private final String filePath = "data" + File.separator + "materias.dat";

    public MateriaDAO() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public List<Materia> getAll() {
        List<Materia> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Materia obj = (Materia) ois.readObject();
                    list.add(obj);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // File doesn't exist or is empty
        }
        return list;
    }

    public void save(Materia materia) {
        List<Materia> list = getAll();
        list.add(materia);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Materia m : list) {
                oos.writeObject(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Materia materia) {
        List<Materia> list = getAll();
        List<Materia> updated = new ArrayList<>();
        for (Materia m : list) {
            if (m.getCodigo().equals(materia.getCodigo())) {
                updated.add(materia);
            } else {
                updated.add(m);
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Materia m : updated) {
                oos.writeObject(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String codigo) {
        List<Materia> list = getAll();
        list.removeIf(m -> m.getCodigo().equals(codigo));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Materia m : list) {
                oos.writeObject(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Materia getByCodigo(String codigo) {
        return getAll().stream()
                .filter(m -> m.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Materia> getBySemestre(int semestre) {
        return getAll().stream()
                .filter(m -> m.getSemestreRecomendado() == semestre)
                .toList();
    }

    public List<Materia> getByProfesor(String profesor) {
        return getAll().stream()
                .filter(m -> m.getProfesor().equals(profesor))
                .toList();
    }
}

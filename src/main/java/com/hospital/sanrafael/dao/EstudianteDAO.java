package com.hospital.sanrafael.dao;

import com.hospital.sanrafael.model.Estudiante;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    private final String filePath = "data" + File.separator + "estudiantes.dat";

    public EstudianteDAO() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public List<Estudiante> getAll() {
        List<Estudiante> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Estudiante obj = (Estudiante) ois.readObject();
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

    public void save(Estudiante estudiante) {
        List<Estudiante> list = getAll();
        list.add(estudiante);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Estudiante e : list) {
                oos.writeObject(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Estudiante estudiante) {
        List<Estudiante> list = getAll();
        List<Estudiante> updated = new ArrayList<>();
        for (Estudiante e : list) {
            if (e.getId().equals(estudiante.getId())) {
                updated.add(estudiante);
            } else {
                updated.add(e);
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Estudiante e : updated) {
                oos.writeObject(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        List<Estudiante> list = getAll();
        list.removeIf(e -> e.getId().equals(id));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Estudiante e : list) {
                oos.writeObject(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Estudiante getById(String id) {
        return getAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Estudiante> getByCarrera(String carrera) {
        return getAll().stream()
                .filter(e -> e.getCarrera().equals(carrera))
                .toList();
    }

    public List<Estudiante> getBySemestre(int semestre) {
        return getAll().stream()
                .filter(e -> e.getSemestre() == semestre)
                .toList();
    }
}

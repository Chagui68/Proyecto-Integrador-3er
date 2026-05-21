package com.hospital.sanrafael.dao;

import com.hospital.sanrafael.model.Doctor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private final String filePath = "data" + File.separator + "doctores.dat";

    public DoctorDAO() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public List<Doctor> getAll() {
        List<Doctor> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Doctor obj = (Doctor) ois.readObject();
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

    public void save(Doctor doctor) {
        List<Doctor> list = getAll();
        list.add(doctor);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Doctor d : list) {
                oos.writeObject(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Doctor doctor) {
        List<Doctor> list = getAll();
        List<Doctor> updated = new ArrayList<>();
        for (Doctor d : list) {
            if (d.getId().equals(doctor.getId())) {
                updated.add(doctor);
            } else {
                updated.add(d);
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Doctor d : updated) {
                oos.writeObject(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        List<Doctor> list = getAll();
        list.removeIf(d -> d.getId().equals(id));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            for (Doctor d : list) {
                oos.writeObject(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Doctor getById(String id) {
        return getAll().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Doctor getByNumeroColegiado(String numeroColegiado) {
        return getAll().stream()
                .filter(d -> d.getNumeroColegiado().equals(numeroColegiado))
                .findFirst()
                .orElse(null);
    }

    public List<Doctor> getByEspecialidad(String especialidad) {
        return getAll().stream()
                .filter(d -> d.getEspecialidad().equals(especialidad))
                .toList();
    }
}

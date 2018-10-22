package infra;

import primitives.Checker;
import primitives.Status;
import primitives.Vector;

import java.util.ArrayList;

interface ICheckerServer {

  Status makeMove(Checker checker, Vector vector);

  Status makeMove(Vector from, Vector to);

  Checker getCheckerPosition(Vector position);

  ArrayList getLocationCheckers();
}

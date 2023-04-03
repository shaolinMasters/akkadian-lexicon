package org.shaolinmasters.akkadianlexicon.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Institution {
  ÁTE("Állatorvostudományi Egyetem"),
  ANNYE("Andrássy Gyula Budapesti Német Nyelvű Egyetem"),
  BCE("Budapesti Corvinus Egyetem"),
  BGE("Budapesti Gazdasági Egyetem"),
  METU("Budapesti Metropolitan Egyetem"),
  BME("Budapesti Műszaki és Gazdaságtudományi Egyetem"),
  DE("Debreceni Egyetem"),
  DRHE("Debreceni Református Hittudományi Egyetem"),
  DUE("Dunaújvárosi Egyetem"),
  EDUTUS("Edutus Egyetem"),
  ELTE("Eötvös Loránd Tudományegyetem"),
  EKKE("Eszterházy Károly Katolikus Egyetem"),
  EHE("Evangélikus Hittudományi Egyetem"),
  GFE("Gál Ferenc Egyetem"),
  KRE("Károli Gáspár Református Egyetem"),
  KJE("Kodolányi János Egyetem"),
  KEE("Közép-európai Egyetem"),
  LFZE("Liszt Ferenc Zeneművészeti Egyetem"),
  MKE("Magyar Képzőművészeti Egyetem"),
  MTE("Magyar Táncművészeti Egyetem"),
  MILTON("Milton Friedman Egyetem"),
  ME("Miskolci Egyetem"),
  MOME("Moholy-Nagy Művészeti Egyetem"),
  NKE("Nemzeti Közszolgálati Egyetem"),
  NJE("Neumann János Egyetem"),
  NYE("Nyíregyházi Egyetem"),
  OE("Óbudai Egyetem"),
  ORZSE("Országos Rabbiképző – Zsidó Egyetem"),
  PE("Pannon Egyetem"),
  PPKE("Pázmány Péter Katolikus Egyetem"),
  PTE("Pécsi Tudományegyetem"),
  SE("Semmelweis Egyetem"),
  SOE("Soproni Egyetem"),
  MATE("Magyar Agrár- és Élettudományi Egyetem"),
  SZE("Széchenyi István Egyetem"),
  SZFE("Színház- és Filmművészeti Egyetem"),
  SZTE("Szegedi Tudományegyetem");
  private final String institutionName;
}

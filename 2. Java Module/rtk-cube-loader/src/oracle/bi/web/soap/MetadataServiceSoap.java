package oracle.bi.web.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 130224.1947.04102)

@WebService(wsdlLocation="http://msk-02-orabits.tsretail.ru:9704/analytics/saw.dll/wsdl/v6",
  targetNamespace="urn://oracle.bi.webservices/v6", name="MetadataServiceSoap")
@XmlSeeAlso(
  { ObjectFactory.class })
public interface MetadataServiceSoap
{
  @WebMethod(action="#clearQueryCache")
  @Action(input="#clearQueryCache", output="urn://oracle.bi.webservices/v6/MetadataServiceSoap/clearQueryCacheResponse")
  @ResponseWrapper(localName="clearQueryCacheResult", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.ClearQueryCacheResult")
  @RequestWrapper(localName="clearQueryCache", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.ClearQueryCache")
  @WebResult(targetNamespace="urn://oracle.bi.webservices/v6", name="result")
  public boolean clearQueryCache(@WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="sessionID")
    String sessionID);

  @WebMethod(action="#getSubjectAreas")
  @Action(input="#getSubjectAreas", output="urn://oracle.bi.webservices/v6/MetadataServiceSoap/getSubjectAreasResponse")
  @ResponseWrapper(localName="getSubjectAreasResult", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.GetSubjectAreasResult")
  @RequestWrapper(localName="getSubjectAreas", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.GetSubjectAreas")
  @WebResult(targetNamespace="urn://oracle.bi.webservices/v6", name="subjectArea")
  public List<SASubjectArea> getSubjectAreas(@WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="sessionID")
    String sessionID);

  @WebMethod(action="#describeSubjectArea")
  @Action(input="#describeSubjectArea", output="urn://oracle.bi.webservices/v6/MetadataServiceSoap/describeSubjectAreaResponse")
  @ResponseWrapper(localName="describeSubjectAreaResult", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeSubjectAreaResult")
  @RequestWrapper(localName="describeSubjectArea", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeSubjectArea")
  @WebResult(targetNamespace="urn://oracle.bi.webservices/v6", name="subjectArea")
  public SASubjectArea describeSubjectArea(@WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="subjectAreaName")
    String subjectAreaName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="detailsLevel")
    SASubjectAreaDetails detailsLevel, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="sessionID")
    String sessionID);

  @WebMethod(action="#describeTable")
  @Action(input="#describeTable", output="urn://oracle.bi.webservices/v6/MetadataServiceSoap/describeTableResponse")
  @ResponseWrapper(localName="describeTableResult", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeTableResult")
  @RequestWrapper(localName="describeTable", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeTable")
  @WebResult(targetNamespace="urn://oracle.bi.webservices/v6", name="table")
  public SATable describeTable(@WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="subjectAreaName")
    String subjectAreaName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="tableName")
    String tableName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="detailsLevel")
    SATableDetails detailsLevel, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="sessionID")
    String sessionID);

  @WebMethod(action="#describeColumn")
  @Action(input="#describeColumn", output="urn://oracle.bi.webservices/v6/MetadataServiceSoap/describeColumnResponse")
  @ResponseWrapper(localName="describeColumnResult", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeColumnResult")
  @RequestWrapper(localName="describeColumn", targetNamespace="urn://oracle.bi.webservices/v6",
    className="oracle.bi.web.soap.DescribeColumn")
  @WebResult(targetNamespace="urn://oracle.bi.webservices/v6", name="column")
  public SAColumn describeColumn(@WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="subjectAreaName")
    String subjectAreaName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="tableName")
    String tableName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="columnName")
    String columnName, @WebParam(targetNamespace="urn://oracle.bi.webservices/v6",
      name="sessionID")
    String sessionID);
}
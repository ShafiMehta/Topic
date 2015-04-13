package resources.utilFiles;

public class Object_Definition_Library {
	public static String getEnvVar(String tag) {
		String tag_Value = Retrieve_EnvVar.ReportGlobalParameters(tag);
		//System.out.println("Tag Value ="+tag_Value);		
		return tag_Value;
	}

public String uf_R_LoginId = "//*[@id='userInput']";
public String uf_R_ServiceSearch_Text = "//*[@id='queryText']";
public String uf_R_SearviceSearch_SearchBtn = "//*[@id='searchForm']/div/div/div[2]/div[3]/input";
public String uf_R_LoginPwd = "//*[@id='passwordInput']";
public String uf_R_LoginBtn = "//*[@id='login-button']";
public String uf_SH_UsernameText_LoginPage ="//*[@id='login-form']/ul/li/label";
public String uf_SH_PasswordText_LoginPage ="//*[@id='login-form']/ul/li[2]/label";
public String uf_SH_CiscoIcon ="//*[@id='fw-logo']/div";
public String uf_N_userNameServiceRequestPage = "//*[@id='username-id']";
public String uf_N_passwordServiceRequestPage = "//*[@id='pwd-id']";
public String uf_N_loginServiceRequestPage = "//*[@name='login']";
public String uf_SH_TopicSearchLogo ="//*[@class='header headerBg headerHt']/a";
public String uf_R_HomePage_Chevron = "//*[@id='lessDataSources']/table/tbody/tr/td/img";
public String uf_R_DownDataSource = "//*[@id='serviceDStable']/table/tbody/tr/td[2]/span";
public String uf_R_UpwardChevron = "//*[@id='serviceDStable']/table/tbody/tr/td[2]/span[2]";
public String uf_R_No = "//*[@id='popupNo']";
public String uf_R_Yes = "//*[@class='popupYes_btn']";
public String uf_R_MovedDataSource1 = "//*[@id='topDStable']/table/tbody/tr[2]/td[6]";
public String uf_R_MovedDataSource = "//*[@id='topDStable']/table/tbody/tr[2]/td[6]/span";
public String uf_R_SearchText = "//*[@id='queryText']";
public String uf_R_SearchBtn = "//*[@id='Searchbtn']";
public String uf_R_C3_ResultCount = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[2]/td[2]/span[2]";
public String uf_R_CDETS_ResultCount = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[3]/td[2]/span[2]";
public String uf_R_Newsgroup_ResultCount = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[4]/td[2]/span[2]";
public String uf_R_Techzone_ResultCount = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[5]/td[2]/span[2]";
public String uf_R_Techzone_ResultCountForOnlyTechZone = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[2]/td[2]/span[2]";
public String uf_R_ResultPageCount = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr/td/table/tbody/tr/td";
public String uf_R_ResutCountInput = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr/td/table/tbody/tr/td";
public String uf_R_ClearAll = "//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[4]/td/a[2]/span";
public String uf_R_SelectAll = "//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[4]/td/a[1]/span";
public String uf_R_TechZone = "//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[5]/td/div/table/tbody/tr/td[7]/input";
public String uf_R_SortBy = "//*[@id='sortCombo']";
public String uf_R_CDETS  = "//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[5]/td/div/table/tbody/tr/td[3]/input";
public String uf_R_AlwaysSearch = "//*[@id='alwaysSearchChk']";
public String uf_R_SearchTip = "//*[@id='lessTips']/table/tbody/tr/td[5]/a";
public String uf_R_SavedSearch = "//*[@id='savedSearch']/tbody/tr/td/span";
public String uf_R_SavedSearch_Data = "//*[@id='searchNamesTable']/tbody/tr/td[2]/a/span"; 
public String uf_R_Rename = "//*[@id='contextMenu']/ul/li/a";
public String uf_R_Delete = "//*[@id='contextMenu']/ul/li[2]/a";
public String uf_R_Share = "//*[@id='contextMenu']/ul/li[3]/a";
public String uf_R_ShareSearchContent = "//*[@id='dialog-boxSave']/div/table/tbody/tr[2]/td";
public String uf_R_Name = "//*[@id='dialog-boxSave']/div/table/tbody/tr[4]/td";
public String uf_R_NameText = "//*[@id='dialog-boxSave']/div/table/tbody/tr[4]/td[2]/input";
public String uf_R_ShareWith = "//*[@id='dialog-boxSave']/div/table/tbody/tr[5]/td";
public String uf_R_ShareWithText = "//*[@id='dialog-boxSave']/div/table/tbody/tr[5]/td[2]/input";
public String uf_R_Comments = "//*[@id='dialog-boxSave']/div/table/tbody/tr[6]/td";
public String uf_R_CommentsText = "//*[@id='dialog-boxSave']/div/table/tbody/tr[6]/td[2]/textarea";
public String uf_R_Save = "//*[@id='dialog-boxSave']/div/table/tbody/tr[9]/td/input";
public String uf_R_Cancel = "//*[@id='dialog-boxSave']/div/table/tbody/tr[9]/td/input[2]";
public String uf_R_C3_HomePage = "//*[@id='C3/CSOne/CARE']";
public String uf_RP_DataSource="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[2]/td[2]/span[1]";
public String uf_R_ServiceRequest="//*[@id='c3buddyContent']/tbody/tr[1]/td[1]/b";
public String uf_R_ProblemDescription="//*[@id='c3buddyContent']/tbody/tr[2]/td[1]/b";
public String uf_R_ResolutionSummary="//*[@id='c3buddyContent']/tbody/tr[3]/td[1]/b";
public String uf_R_CDETSTopicBuddy="//*[@id='topicBuddyDiv']";
public String uf_R_TopicBuddyClose = "//*[@id='topicBuddyclose']";
public String uf_R_ChevronTopicBuddy = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[2]/img";

public String uf_R_ChevronTopicBuddy_IMG = "//*[@class='chevronTd']";
public String uf_R_C3ResultContent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]";
public String uf_R_C3 = "//*[@id='C3/CSOne/CARERepo']";
public String uf_R_NewsGroup = "//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[4]/td[2]/input";
public String uf_R_CDTS = "//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[3]/td[2]/input";
public String uf_R_Techzone = "//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[5]/td[2]/input";
public String uf_R_FileFormat = "//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[2]/table/tbody/tr[1]/td/span";
public String uf_R_C3Discription = "";
public String uf_R_MoreDataSource = "";
public String uf_R_LessDataSource = "";
public String uf_SH_ErrorMessagewithoutCredentials="//*[@id='warning-msg']/h3";
public String uf_R_MoreTips = "//*[@id='resultsForm']/table/tbody/tr[2]/td[2]/div/table/tbody/tr/td[2]/div[1]/table/tbody/tr/td[5]/a";
public String uf_R_LessTips = "//*[@id='moreTips']/table/tbody/tr/td[5]/a";

public String uf_R_Reset = "//*[@id='resetPopUp']/span";
public String uf_R_DatasourceColoumnOne = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/label";
public String uf_R_hyperlink = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/a";
public String uf_R_NewWindow = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/a[2]/img";
public String uf_R_VeryRelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[2]/label";
public String uf_R_SelectVeryRelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[2]/input";
public String uf_R_SelectVeryReleventToolTip = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[2]";
public String uf_R_SomeWhatRelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[3]/label";
public String uf_R_SelectSomeWhatRelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[3]/input";
public String uf_R_SelectSomeWhatReleventToolTip = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[3]";

public String uf_R_Uncertain = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[4]/label";
public String uf_R_SelectUncertain = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[4]/input";
public String uf_R_SelectUncertainToolTip = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[4]";

public String uf_R_Irrelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[5]/label";
public String uf_R_SelectIrrelevent = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[5]/input";
public String uf_R_SelectIrreleventToolTip = "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[2]/table/tbody/tr[1]/td[5]";
public String uf_R_FeedbackRecieved = "//*[@id='2_inMsg']/b/font";



public String uf_R_DataSoureFilter = "//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table/tbody/tr/td";
public String uf_R_DateRangeFilter = "//*[@id='Date RangeDiv']/table/tbody/tr[1]/td/span";

public String uf_R_DateRangeFirst_ResultCount = "//*[@id='Date RangeDiv']/table/tbody/tr[3]/td[2]/ul/li/span[2]";
public String uf_R_DateRangeSecond_ResultCount = "//*[@id='Date RangeDiv']/table/tbody/tr[4]/td[2]/ul/li/span[2]";
public String uf_R_DateRangeThird_ResultCount = "//*[@id='Date RangeDiv']/table/tbody/tr[5]/td[2]/ul/li/span[2]";
public String uf_R_DateRangeFourth_ResultCount = "//*[@id='Date RangeDiv']/table/tbody/tr[6]/td[2]/ul/li/span[2]";

public String uf_R_DataSourceFilterAll = "//*[@id='allRepo']";

public String uf_R_BugId="//*[@id='cdetsbuddyContent']/tbody/tr[1]/td[1]/b";
public String uf_R_Status="//*[@id='cdetsbuddyContent']/tbody/tr[1]/td[2]/b";
public String uf_R_Severity="//*[@id='cdetsbuddyContent']/tbody/tr[1]/td[3]/b";
public String uf_R_ProductComponent="//*[@id='cdetsbuddyContent']/tbody/tr[2]/td[2]/b";
public String uf_R_Version="//*[@id='cdetsbuddyContent']/tbody/tr[4]/td[1]/b";
public String uf_R_IntegratedReleases="//*[@id='cdetsbuddyContent']/tbody/tr[5]/td[1]/b";
public String uf_R_LastChanged="//*[@id='cdetsbuddyContent']/tbody/tr[2]/td[1]/b";
public String uf_R_TroubleTickets="//*[@id='cdetsfieldbuddyContent']/tbody/tr[1]/td[1]/b";
public String uf_R_DEManager="//*[@id='cdetsbuddyContent']/tbody/tr[3]/td[3]/b";
public String uf_R_Found="//*[@id='cdetsbuddyContent']/tbody/tr[3]/td[2]/b";
public String uf_R_Engineer="//*[@id='cdetsbuddyContent']/tbody/tr[2]/td[3]/b";
public String uf_R_Submitter="//*[@id='cdetsbuddyContent']/tbody/tr[3]/td[1]/b";
public String uf_R_ReleaseNote="//*[@id='cdetsfieldbuddyContent']/tbody/tr[2]/td[1]/b";
public String uf_R_DescriptionNote="//*[@id='cdetsfieldbuddyContent']/tbody/tr[3]/td[1]/b";
public String uf_R_ExternalCustomerVisibilityStatus="//*[@id='cdetsfieldbuddyContent']/tbody/tr[4]/td[1]/b";
public String uf_SH_AdvanceSearch_QuestionMark="//*[@id='spanadvSearch']/b/a";

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////nisahni/////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public String uf_N_userName="//*[@id='userInput']";
public String uf_N_password="//*[@id='passwordInput']";
public String uf_N_login="//*[@id='login-button']";
public String uf_N_queryText="//*[@id='queryText']";
public String uf_N_searchButton="//*[@id='Searchbtn']";
public String uf_N_correspondingTotalResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr/td/table/tbody/tr/td";
public String uf_SH_PDFChechox="//*[@id='PDFs']";
public String uf_SH_PDF_NoofResults="//*[@id='File FormatDiv']/table/tbody/tr[5]/td[2]/ul/li/span[2]";
public String uf_SH_searchResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td";
public String uf_SH_Date_Latest="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/a[1]";
public String uf_SH_Date_Oldest="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/a[2]";
public String uf_SH_Relevance="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[1]/td/table/tbody/tr/td[2]/a[2]";
public String uf_SH_DateBefore="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[2]/label[2]";
public String uf_SH_DateBeforeSourcefire="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[2]";
public String uf_SH_DateAfter="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[2]/label[2]";
public String uf_SH_DateAfterSourcefire="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[2]";
public String uf_SH_application_vndChechox="//*[@id='PPTx']";
public String uf_SH_application_vndChechoxpowerpoint="//*[@id='application/vnd.ms-powerpoint']";

public String uf_SH_ShowAllLink="//*[@id='a-File Format']";
public String uf_SH_application_vnd_NoofResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[2]/table/tbody/tr[4]/td[2]/ul/li/span[2]";
public String uf_SH_webpages_NoofResults="//*[@id='File FormatDiv']/table/tbody/tr[3]/td[2]/ul/li/span[2]";
public String uf_SH_webpagesChechox="//*[@id='Webpages']";
public String uf_SH_noOfC3Results="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[2]/td[2]/span[2]";
public String uf_SH_noOfCDETSResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[3]/td[2]/span[2]";
public String uf_SH_noOfNewgroupResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[4]/td[2]/span[2]";
public String uf_SH_noOfTechZoneResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/table[2]/tbody/tr[5]/td[2]/span[2]";
public String uf_SH_DynamicSoft="//*[@id='span_Dynamicsoft']";
public String uf_SH_Starent="//*[@id='span_Starent']";
public String uf_SH_Composite="//*[@id='span_Composite']";
public String uf_SH_Latitude="//*[@id='span_Latitude']";
public String uf_SH_LessdataSourceLink="//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[7]/td/div[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/a/span";
public String uf_SH_AllChechox="//*[@id='All|mimetype']";
public String uf_SH_PreviewLink="//*[@class='selResult']/td/div/label[3]";
public String uf_SH_NewsgroupHeader_TopicPreview="//*[@id='newsbuddyHeader']/label";
public String uf_SH_CDETS_TopicPreview_Header="//*[@id='buddyHeader']/label";
public String uf_SH_C3_TopicPreview_Header="//*[@id='topicBuddyDiv']/div[1]/div/span[2]/label[1]";
public String uf_SH_CDETS_TopicPreview_MoreLink="//*[@id='cdetsfieldbuddyContent']/tbody/tr[2]/td[2]/a";
public String uf_SH_C3_TopicPreview_MoreLink="//*[@id='c3cdetsbuddyContent']/tbody/tr/td[2]/a";
public String uf_SH_CDETS_TopicPreview_LessLink="//*[@id='cdetsfieldbuddyContent']/tbody/tr[2]/td[2]/a";
public String uf_SH_C3_TopicPreview_LessLink="//*[@id='c3cdetsbuddyContent']/tbody/tr/td[2]/a";
public String uf_SH_CDETSHeader_TopicPreview="//*[@id='buddyHeader']/label";
public String uf_SH_NewsgroupChevron_TopicPreview="//*[@class='selResult']/td[2]/img";
public String uf_SH_CDETS_TopicPreview_Link="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/label[4]";
public String uf_SH_C3_TopicPreview_Link="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/label[5]";

public String uf_SH_Newsgroupresult1="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[1]/td[2]/label[1]";
public String uf_SH_CDETSresult1="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[2]";
public String uf_SH_C3result1="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[2]/label[2]";
public String uf_SH_Techzoneresult1="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[2]";
public String uf_SH_editLinkDatasource="//*[@id='Save']/span";
public String uf_SH_editLinkDatasourcePopUp="//*[@id='editDSdialog']/div/table/tbody/tr/td/div";
public String uf_SH_editDynamicSoftPopUp="//*[@id='Dynamicsoft']";
public String uf_SH_editSourcefirePopUp="//*[@id='Sourcefire']";
public String uf_SH_editUbiquisysPopUp="//*[@id='Ubiquisys']";
public String uf_SH_editSourcefireSoftPopUp="//*[@id='Sourcefire']";
public String uf_SH_editOKButtonPopUp="//*[@id='resultsForm']/div[7]/table/tbody/tr[2]/td/input[1]";
public String uf_SH_PriorityFilterAfterEdit="//*[@id='PriorityDiv']/table/tbody/tr/td/span";
public String uf_SH_editCancelButtonPopUp="//*[@id='resultsForm']/div[7]/table/tbody/tr[2]/td/input[2]";
public String uf_SH_editResetButtonPopUp="//*[@id='resultsForm']/div[7]/table/tbody/tr[2]/td/input[3]";
public String uf_SH_editSelectAllPopUp="//*[@id='editFiltersdialog']/div/table/tbody/tr[5]/td[1]/table/tbody/tr/td[1]/div[2]/a[1]/span";
public String uf_SH_editClearAllPopUp="//*[@id='editFiltersdialog']/div/table/tbody/tr[5]/td[1]/table/tbody/tr/td[1]/div[2]/a[2]/span";

public String uf_SH_editResetToDefaultPopUp="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[3]";
public String uf_SH_editButton4downPopUp="//*[@id='lastBtn']";
public String uf_SH_editButton3downPopUp="//*[@id='downBtn']";
public String uf_SH_editButton2downPopUp="//*[@id='upBtn']";
public String uf_SH_editButton1downPopUp="//*[@id='firstBtn']";

/////////////////////////////////////////************Edit Filter Verification Pop Up**************//////////////////////////////////
public String uf_SH_editOKButtonPopUp_Techzone="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[1]";
public String uf_SH_editCancelButtonPopUp_Techzone="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[2]";
public String uf_SH_editResetButtonPopUp_Techzone="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[3]";
//public String uf_SH_editSelectAllPopUp="//*[@id='editFiltersdialog']/div/table/tbody/tr[5]/td[1]/table/tbody/tr/td[1]/div[2]/a[1]/span";
//public String uf_SH_editClearAllPopUp="//*[@id='editFiltersdialog']/div/table/tbody/tr[5]/td[1]/table/tbody/tr/td[1]/div[2]/a[2]/span";
/*public String uf_SH_editButton4downPopUp="//*[@id='lastBtn']";
public String uf_SH_editButton3downPopUp="//*[@id='downBtn']";
public String uf_SH_editButton2downPopUp="//*[@id='upBtn']";
public String uf_SH_editButton1downPopUp="//*[@id='firstBtn']";
*/
public String uf_SH_editalwaysFiltersPopUp="//*[@id='alwaysFilters']";
public String uf_SH_editPriorityFilterChechboxPopUp="//*[@id='Priority']";
public String uf_SH_FilterContainingArea="//*[@id='filterDiv']";
public String uf_SH_PriorityFilter="//*[@id='PriorityDiv']/table/tbody/tr/td/span";
public String uf_SH_AuthorFilterChkbox_Techzone="//*[@id='filtersTbl']/tbody/tr[1]/td/input";
public String uf_SH_AuthorFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[2]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_StyleFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[3]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_StateFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[4]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_BoardFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[6]/table/tbody/tr[1]/td/span[1]";

public String uf_SH_HasLinkedSRFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[7]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_HadKudosFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[8]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_CountofLinkedSRFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[9]/table/tbody/tr[1]/td/span[1]";
public String uf_SH_LinkedSRFilter_Techzone="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[10]/table/tbody/tr[1]/td/span[1]";

public String uf_SH_editCDETS_DDTS_PopUp="//*[@id='CDETS/DDTS']";
public String uf_SH_editC3_CSOne_CAREPopUp="//*[@id='C3/CSOne/CARE']";
public String uf_SH_editSourceFirePopUp="//*[@id='Sourcefire']";
public String uf_SH_editUbiquisysPopUp1="//*[@id='Ubiquisys']";
public String uf_SH_editTech_ZonePopUp="//*[@id='Tech Zone']";
public String uf_SH_editNewsgroupPopUp="//*[@id='Newsgroup']";
public String uf_SH_asapediaPopUp="//*[@id='Asapedia']";
public String uf_SH_alwaysSearchChkBox="//*[@id='alwaysSearchChk']";

public String uf_SH_LastYear_Filter = "//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[1]/table/tbody/tr[3]/td[2]/ul/li/span[1]";
public String uf_SH_LastYear_Filter_noOfResults = "//*[@id='filterDiv']/div[1]/table/tbody/tr[3]/td[2]/ul/li/span[2]";

public String uf_SH_ThisYear_Filter_noOfResults = "//*[@id='filterDiv']/div[1]/table/tbody/tr[5]/td[2]/ul/li/span[2]";
public String uf_SH_BeforeLastYear_Filter_RadioButton="//*[@id='Before Last Year']";
public String uf_SH_LastYear_Filter_RadioButton="//*[@id='Last Year']";

public String uf_SH_ThisYear_Filter_RadioButton="//*[@id='This Year']";
public String uf_SH_DateRange_AllFilter = "//*[@id='filterDiv']/div[1]/table/tbody/tr[2]/td[1]/span/input";
public String uf_SH_c3PopUp="//*[@id='C3/CSOne/CARE']";
public String uf_SH_cdetsPopUp="//*[@id='CDETS/DDTS']";
public String uf_SH_newsgroupPopUp="//*[@id='Newsgroup']";
public String uf_SH_techzonePopUp="//*[@id='Tech Zone']";
public String uf_SH_spreadsheet_NoofResults="//*[@id='File FormatDiv']/table/tbody/tr[4]/td[2]/ul/li/span[2]";
public String uf_SH_Online_NoofResults="//*[@id='resultsForm']/table/tbody/tr[9]/td[1]/div/div[1]/div[2]/table/tbody/tr[7]/td/div/table/tbody/tr[2]/td[2]/ul/li/span[2]";
public String uf_SH_OnlineTrainingChechox="//*[@id='Online Training']";
public String uf_SH_MyPreferencesLink="//*[@class='header headerBg headerHt']/span[2]/span[2]/a[2]";
public String uf_SH_MyPreferences_BrowserHistory_RadioButton="//*[@id='maindiv']/div/table/tbody/tr/td/input";
public String uf_SH_MyPreferences_SystemData_RadioButton="//*[@id='maindiv']/div/table/tbody/tr[2]/td/input";
public String uf_SH_MyPreferences_BackButton="//*[@id='maindiv']/div/table/tbody/tr/td/input[3]";
public String uf_SH_MyPreferences_SaveChanges="//*[@id='Save']";
public String uf_SH_MyPreferences_Message="//*[@id='maindiv']/div/div/b/font";
public String uf_SH_AutosuggestValue="//*[@id='autoSuggest1']";
public String uf_SH_AutosuggestValue1="//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[2]/td/bdo/div/div/div[1]/div[1]";
public String uf_SH_ClickInCenter="//*[@id='searchForm']/table/tbody/tr[2]/td[2]";

public String uf_SH_Shoguncheckbox="//*[@id='Shogun']";
public String uf_SH_Tandbergcheckbox="//*[@id='Tandberg']";
public String uf_SH_Newscalecheckbox="//*[@id='Newscale']";
public String uf_SH_TSTrainingcheckbox="//*[@id='TS Training']";
public String uf_SH_Whiptailcheckbox="//*[@id='Whiptail']";
public String uf_SH_Asapediacheckbox="//*[@id='Asapedia']";
public String uf_SH_Sourcefirecheckbox="//*[@id='Sourcefire']";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////----------------DATASOURCES----------------////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public String uf_N_datasources="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table/tbody/tr/td";
public String uf_N_allChkBox="//*[@id='allRepo']";
public String uf_SH_allChkBox_AllDataSource="//*[@id='allRepo']";
public String uf_N_newsGroupChkBox="//*[@id='NewsgroupRepo']";
public String uf_N_ubiquisysChkBox="//*[@id='UbiquisysRepo']";
public String uf_N_c3ChkBox="//*[@id='C3/CSOne/CARERepo']";
public String uf_SH_EditFilterLink="//*[@id='editFilters']/span";
public String uf_SH_Edit_OKButton="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[1]";
public String uf_SH_Edit_CancelButton="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[2]";
public String uf_SH_Edit_ResetButton="//*[@id='editFiltersdialog']/div/table/tbody/tr[8]/td/span/input[3]";
public String uf_SH_dateRangeFilterforCDETS="//*[@id='filterDiv']/div/table/tbody/tr/td/span";
public String uf_SH_CloseButtondateRangeFilterforCDETS="//*[@id='filterDiv']/div/table/tbody/tr/td/span[2]/img";
public String uf_N_techzoneChkBox="//*[@id='Tech ZoneRepo']";
public String uf_N_cdetsChkBox="//*[@id='CDETS/DDTSRepo']";
public String uf_N_dynamicSoftChkBox="//*[@id='DynamicsoftRepo']";
public String uf_N_SourcefireChkBox="//*[@id='SourcefireRepo']";
public String uf_N_C3NoOfResults="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[2]/td/span[2]";
public String uf_N_CDETSNoOfResults="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[3]/td/span[2]";
public String uf_N_newsGropupNoOfResults="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[4]/td/span[2]";
public String uf_N_techzoneNoOfResults="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[5]/td/span[2]";
public String uf_N_allDataSourcePresent="//*[@id='Date RangeDiv']/table/tbody";
public String uf_N_dynamicSoft="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[4]/td/span";
public String uf_N_asapedia="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[2]/td/span";

public String uf_SH_C3_CSOne_CAREcheckbox="//*[@id='C3/CSOne/CARE']";
public String uf_SH_CDETS_DDTScheckbox="//*[@id='CDETS/DDTS']";
public String uf_SH_Techzonecheckbox="//*[@id='Tech Zone']";
public String uf_SH_Newsgroupcheckbox="//*[@id='Newsgroup']";
public String uf_SH_TACFieldcheckbox="//*[@id='TAC FieldAlerts']";
public String uf_SH_TSTrainingHomecheckbox="//*[@id='TS Training']";

public String uf_SH_clearAll="//*[@id='clearAll']";
public String uf_N_cachedLink="//*[@class='resultTd']/div/a[2]";
public String uf_N_help="//*[@class='header headerBg headerHt']/span[2]/span[2]/a[3]";
public String uf_N_support="//*[@class='header headerBg headerHt']/span/span[2]/a[5]";
public String uf_SH_CDETSIntegratedReleases="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[6]/td[2]/label[1]";
public String uf_SH_CDETSVersion="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[7]/td[2]/label[1]";
public String uf_N_helpUserGuide="//body/h1";


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////--------------FILTERS----------------////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////-----------------DATERANGE----------------////////////////////////////////////////////////
public String uf_N_dateRange="//*[@id='Date RangeDiv']/table/tbody/tr/td/span";
public String uf_N_allDateRangeFilter="//*[@id='Date RangeDiv']/table/tbody/tr[2]/td[2]/ul/li/span";
//public String uf_N_moreThan2YrsTotalResults="//*[@id='Date RangeDiv']/table/tbody/tr[3]/td[2]/ul/li/span[2]";
//public String uf_N_lastyearTotalResults="//*[@id='Date RangeDiv']/table/tbody/tr[4]/td[2]/ul/li/span[2]";
//public String uf_N_thisyearTotalResults="//*[@id='Date RangeDiv']/table/tbody/tr[5]/td[2]/ul/li/span[2]";
//public String uf_N_lastmonthTotalResults="//*[@id='Date RangeDiv']/table/tbody/tr[6]/td[2]/ul/li/span[2]";
public String uf_N_show_MoreLess_DateRange="//*[@id='a-Date Range']";
public String uf_N_radioButtonResults="//*[@id='Date RangeDiv']/table/tbody/tr";
public String uf_N_lastweekTotalResults="//*[@id='navi-Date Range']/table/tbody/tr/td[2]/ul/li/span[2]";
public String uf_N_thisweekTotalResults="//*[@id='navi-Date Range']/table/tbody/tr[2]/td[2]/ul/li/span[2]";

public String uf_N_allRadioButton="//*[@id='Date RangeDiv']/table/tbody/tr/td/span";
//public String uf_N_moreThan2YrsRadioButton="//*[@id='Date RangeDiv']/table/tbody/tr[3]/td[2]/ul/li/span[2]";
//public String uf_N_lastyearRadioButton="//*[@id='Date RangeDiv']/table/tbody/tr[4]/td[2]/ul/li/span[2]";
//public String uf_N_thisyearRadioButton="//*[@id='Date RangeDiv']/table/tbody/tr[5]/td[2]/ul/li/span[2]";
//public String uf_N_lastmonthRadioButton="//*[@id='Date RangeDiv']/table/tbody/tr[6]/td[2]/ul/li/span[2]";
//public String uf_N_lastweekRadioButton="//*[@id='navi-Date Range']/table/tbody/tr/td[2]/ul/li/span[2]";
//public String uf_N_thisweekRadioButton="//*[@id='navi-Date Range']/table/tbody/tr[2]/td[2]/ul/li/span[2]";
public String uf_N_fromRadioButton="//*[@id='fromToRadio']";
//public String uf_N_toRadioButton="//*[@id='navi-Date Range']";

public String uf_N_fromDateRange="//*[@id='Date RangeDiv']/table/tbody/tr[5]/td[2]/ul/li/table/tbody/tr/td/input";
public String uf_N_toDateRange="//*[@id='Date RangeDiv']/table/tbody/tr[5]/td[2]/ul/li/table/tbody/tr[2]/td/input";
public String uf_N_all_DateRangePresent="//*[@id='Date RangeDiv']/table/tbody";
public String uf_N_allDateRangeChkbox="//*[@id='All']";

public String uf_N_fromDate="//*[@class='ui-datepicker-calendar']/tbody/tr[1]/td[6]/a";
public String uf_N_toDate="//*[@id='ui-datepicker-div']/table/tbody/tr[4]/td[7]/a";
public String uf_N_fromDate_Textbox="//*[@id='fromDate']";
public String uf_N_fromDateMonth_Textbox="//*[@class='ui-datepicker-month']";
public String uf_N_fromDateSelectMonth_Textbox="//*[@class='ui-datepicker-month']/option[11]";

public String uf_N_fromDateYear_Textbox="//*[@class='ui-datepicker-year']";
public String uf_N_fromDateSelectYear_Textbox="//*[@class='ui-datepicker-year']/option[103]";

//////////////////////////////////////-----------------FILEFORMAT----------------////////////////////////////////////////////////
public String uf_N_fileformat="//*[@id='File FormatDiv']/table/tbody/tr/td/span";
public String uf_N_webPagesTotalResults="//*[@id='File FormatDiv']/table/tbody/tr[3]/td[2]/ul/li/span[2]";
public String uf_N_pdfTotalResults="//*[@id='File FormatDiv']/table/tbody/tr[4]/td[2]/ul/li/span[2]";
public String uf_N_msPowerPointTotalResults="//*[@id='File FormatDiv']/table/tbody/tr[5]/td[2]/ul/li/span[2]";
public String uf_N_msWordTotalResults="//*[@id='File FormatDiv']/table/tbody/tr[6]/td[2]/ul/li/span[2]";
public String uf_N_show_MoreLess_FileFormat="//*[@id='a-File Format']";
public String uf_N_wordTotalResults="//*[@id='navi-File Format']/table/tbody/tr/td[2]/ul/li/span[2]";
public String uf_N_spreadsheetsTotalResults="//*[@id='navi-File Format']/table/tbody/tr[2]/td[2]/ul/li/span[2]";
public String uf_SH_FAQLink="//*[@class='header headerBg headerHt']/span[2]/span[2]/a[4]";
public String uf_SH_AdvanceSearch_QuestionMark_ResultPage="//*[@id='span_advSearch']/b/a";



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////--------------Search CSO----------------////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////----------------DATASOURCES----------------////////////////////////////////////////////////

public String uf_N_bugDatabase_CSO="//*[@id='searchForm']/div/div[2]/div/div/div/input";
public String uf_N_TAC_CSO="//*[@id='searchForm']/div/div[2]/div/div/div/input[2]";
public String uf_N_newsgroup_CSO="//*[@id='searchForm']/div/div[2]/div/div/div/input[3]";
public String uf_N_techzone_CSO="//*[@id='searchForm']/div/div[2]/div/div/div[2]/input";
public String uf_N_searchButton_CSO="//*[@id='searchForm']/div/div/div[2]/div[3]/input";
public String uf_N_AttivioDS_CSO="//*[@id='attivioTab']";
	
/////////////////////////////////////////----------------Meta Data for CDETS----------------////////////////////////////////////////////////

public String uf_N_component_CSO="//*[@class='resultTd']/table/tbody/tr/td[2]/label";
public String uf_N_DEManager_CSO="//*[@class='resultTd']/table/tbody/tr/td[4]/label";
public String uf_N_Date_CSO="//*[@class='resultTd']/table/tbody/tr/td[6]/label";
public String uf_N_engineer_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[2]/label";
public String uf_N_found_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[4]/label";
public String uf_N_keywords_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[6]/label";
public String uf_N_lastChanged_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[2]/label";
public String uf_N_product_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[4]/label";
public String uf_N_project_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[6]/label";
public String uf_N_severity_CSO="//*[@class='resultTd']/table/tbody/tr[4]/td[2]/label";
public String uf_N_status_CSO="//*[@class='resultTd']/table/tbody/tr[4]/td[4]/label";
public String uf_N_submitterId_CSO="//*[@class='resultTd']/table/tbody/tr[4]/td[6]/label";
public String uf_N_troubleTickets_CSO="//*[@class='resultTd']/table/tbody/tr[5]/td[2]/label";
public String uf_N_integratedReleases_CSO="//*[@class='resultTd']/table/tbody/tr[6]/td[2]/label";
public String uf_N_version_CSO="//*[@class='resultTd']/table/tbody/tr[7]/td[2]/label";

/////////////////////////////////////////----------------Meta Data for C3----------------////////////////////////////////////////////////

public String uf_N_company_CSO="//*[@class='resultTd']/table/tbody/tr/td[2]/label";
public String uf_N_contractId_CSO="//*[@class='resultTd']/table/tbody/tr/td[4]/label";
public String uf_N_contractStatus_CSO="//*[@class='resultTd']/table/tbody/tr/td[6]/label";
public String uf_N_dateC3_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[2]/label";
public String uf_N_owner_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[4]/label";
public String uf_N_priority_CSO="//*[@class='resultTd']/table/tbody/tr[2]/td[6]/label";
public String uf_N_problemCode_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[2]/label";
public String uf_N_resolutionCode_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[4]/label";
public String uf_N_SRState_CSO="//*[@class='resultTd']/table/tbody/tr[3]/td[6]/label";
public String uf_N_serialNo_CSO="//*[@class='resultTd']/table/tbody/tr[4]/td[2]/label";
public String uf_N_softwareTypeCSO="//*[@class='resultTd']/table/tbody/tr[4]/td[4]/label";
public String uf_N_softwareVersion_CSO="//*[@class='resultTd']/table/tbody/tr[4]/td[6]/label";
public String uf_N_statusC3_CSO="//*[@class='resultTd']/table/tbody/tr[5]/td[2]/label";
public String uf_N_subtechnology_CSO="//*[@class='resultTd']/table/tbody/tr[5]/td[4]/label";
public String uf_N_technology_CSO="//*[@class='resultTd']/table/tbody/tr[5]/td[6]/label";

/////////////////////////////////////////----------------Topic Buddy for C3----------------////////////////////////////////////////////////

public String uf_N_topicBuddy="//*[@id='topicBuddyDiv']";
public String uf_N_serviceRequest_topicBuddy_C3="//*[@id='c3buddyContent']/tbody/tr/td/b";
public String uf_N_bugID_topicBuddy_CDETS="//*[@id='cdetsbuddyContent']/tbody/tr/td/b";
public String uf_N_topicBuddy_Newsgroup="//*[@id='NBContent']";
public String uf_N_topicBuddy_techzone="//*[@id='buddyContentDiv']";
public String uf_N_advanceSearch="//*[@id='span_advSearch']/b";

/////////////////////////////////////////----------------Saved Seacrh----------------////////////////////////////////////////////////

public String uf_N_savedSearchPosition="//*[@id='savedSearch']/tbody/tr/td/span";
public String uf_N_saveLink="//*[@id='saveLink']/span";

public String uf_N_nameDialogBox="//*[@id='dialog-boxSave']/div/table/tbody/tr[4]/td";
public String uf_N_saveDialogBox="//*[@id='Save']";
public String uf_N_cancelDialogBox="//*[@id='popupNo']";
public String uf_N_searchQueryDialogBox="//*[@id='srchname']";
public String uf_N_shareWithDialogbox="//*[@id='share']";
public String uf_N_savedSearchScrollbar="//*[@id='savedSearchDiv']/table/tbody/tr";

public String uf_N_renameDialogbox="//*[@id='share']";
public String uf_N_cancel_RenameDialogBox="//*[@id='savedSearchDiv']/table/tbody/tr";
public String uf_N_name_RenameDialogBox="//*[@id='newName']";

public String uf_N_Yes_DeleteDialogBox="//*[@id='deleteSearch']";
public String uf_N_No_DeleteDialogBox="//*[@id='popupNo']";

public String uf_N_savedSearchContent="//*[@id='savedSearchDiv']";

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////shafmeht///////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


public String uf_SH_advancedSearchCheckbox="//*[@id='advSearch']";
public String uf_SH_errorColonMessage="//body/form/table/tbody/tr[9]/td[2]/table/tbody/tr/td";
public String uf_SH_statusInMetafield="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[4]/td[6]/label";
public String uf_SH_statusUInMetafield="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[4]/td[6]/label";

////////////////////////////////////////////////Results ///////////////////////////////////////////
public String uf_SH_resultDatasource="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/label";
public String uf_SH_resultTitle="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/a";

////////////////////////////////////////////---- Help and Support Links-------------------///////////////
public String uf_SH_helpLink="//*[@class='header headerBg headerHt']/span[2]/a[2]";
public String uf_SH_SupportLink="//*[@class='header headerBg headerHt']/span[2]/a[3]";
public String uf_SH_TopicSearch_3_userGuide ="//body/h1";
public String uf_SH_SimpleSearch ="//body/a";


////////////////////////////////////////// More and less Tips////////////////////////////////////////
public String uf_SH_randomTip="//*[@id='lessTips']/table/tbody/tr/td";
public String uf_SH_moreTips="//*[@id='lessTips']/table/tbody/tr/td[5]/a";
public String uf_SH_lessTips="//*[@id='moreTips']/table/tbody/tr/td[5]/a";
public String uf_SH_IOScrash="//*[@id='moreTips']/table/tbody/tr/td";
public String uf_SH_IOSANDcrash="//*[@id='moreTips']/table/tbody/tr[2]/td";
public String uf_SH_IOScrashORexception="//*[@id='moreTips']/table/tbody/tr[3]/td";
public String uf_SH_cat65="//*[@id='moreTips']/table/tbody/tr[4]/td";
public String uf_SH_cat6="//*[@id='moreTips']/table/tbody/tr[5]/td";
public String uf_SH_12SxI="//*[@id='moreTips']/table/tbody/tr[6]/td";
public String uf_SH_12SXI="//*[@id='moreTips']/table/tbody/tr[7]/td";
public String uf_SH_IOStraceback="//*[@id='moreTips']/table/tbody/tr[8]/td";
public String uf_SH_StatusClosed="//*[@id='moreTips']/table/tbody/tr[9]/td";

public String uf_SH_IOScrash_Sourcefire="//*[@id='moreTips']/table/tbody/tr/td";
public String uf_SH_IOSANDcrash_Sourcefire="//*[@id='moreTips']/table/tbody/tr/td";
public String uf_SH_IOScrashORexception_Sourcefire="//*[@id='moreTips']/table/tbody/tr[2]/td";
public String uf_SH_cat65_Sourcefire="//*[@id='moreTips']/table/tbody/tr[3]/td";
public String uf_SH_cat6_Sourcefire="//*[@id='moreTips']/table/tbody/tr[4]/td";
public String uf_SH_12SxI_Sourcefire="//*[@id='moreTips']/table/tbody/tr[5]/td";
public String uf_SH_12SXI_Sourcefire="//*[@id='moreTips']/table/tbody/tr[6]/td";
public String uf_SH_IOStraceback_Sourcefire="//*[@id='moreTips']/table/tbody/tr[7]/td";
public String uf_SH_StatusClosed_Sourcefire="//*[@id='moreTips']/table/tbody/tr[8]/td";


public String uf_SH_resultDisplayesForQuery="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/label[3]";

////////////////////////////////////////////////// XML KWERY-------////////////////////////////
public String uf_SH_C3XMLKwery="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div/label[3]/a";
public String uf_SH_CS_0ne="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/label[3]/a[2]";
public String uf_SH_XMLKweryPage="//*[@class='ui-layout-center ui-layout-pane ui-layout-pane-center ui-layout-pane-hover ui-layout-pane-center-hover ui-layout-pane-open-hover ui-layout-pane-center-open-hover']/span";
public String uf_SH_C3StatusNone="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[8]/td/table/tbody/tr[5]/td[4]/label[2]";
public String uf_SH_C3SRContactName="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[6]/label[1]";
public String uf_SH_TechZoneArticleTypeNone="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[1]/td[2]/label[2]";
public String uf_SH_CDETS_DDTSKeywordsNone="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[5]/td[4]/label[2]";
public String uf_SH_Newsgroupdate="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[5]/td/table/tbody/tr/td[2]/label[2]";
public String uf_SH_NewsgroupNext="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[23]/td/div/div/a[9]";
public String uf_SH_NewsgroupPrevious="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[23]/td/div/div/span";
///////////////////////////////////////////---------FEEDBACK------/////////////////////////////////////////////////

public String uf_SH_veryRelevantradioButton="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div[2]/table/tbody/tr/td[2]/input";
public String uf_SH_veryRelevantradioButton_Ubiquisys="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/div[3]/table/tbody/tr/td[2]/input";
public String uf_SH_somewhatRelevantradioButton="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[4]/td/div[2]/table/tbody/tr/td[3]/input";
public String uf_SH_somewhatRelevantradioButton_Ubiquisys="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[4]/td/div[3]/table/tbody/tr/td[3]/input";
public String uf_SH_uncertainradioButton="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[5]/td/div[2]/table/tbody/tr/td[4]/input";
public String uf_SH_uncertainradioButton_Ubiquisys="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[5]/td/div[3]/table/tbody/tr/td[4]/input";
public String uf_SH_irrelevantradioButton="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[6]/td/div[2]/table/tbody/tr/td[5]/input";
public String uf_SH_irrelevantradioButton_Ubiquisys="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[6]/td/div[3]/table/tbody/tr/td[5]/input";
public String uf_SH_feedbackReceieved="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[6]/td/div[2]/table/tbody/tr[2]/td/div/b/font";


///////////////////////////////////////// ------------------EDIT DATSOURCES-------------------------////////////////////



public String uf_SH_ResultpagequeryText="//*[@id='resultsForm']/table/tbody/tr[4]/td[2]/input";
public String uf_SH_dateInMetaFields="//body/form/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td/table/tbody/tr[2]/td[4]/label[2]";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////----------------DATASOURCES----------------////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public String uf_SH_moreDataSourcesLink="//*[@id='searchForm']/table/tbody/tr[4]/td[2]/table/tbody/tr[7]/td/div[1]/table/tbody/tr/td[2]/a/span";

public String uf_SH_alwaysSearchWithinLink="//*[@id='lessDataSources']/table/tbody/tr/td[4]";
public String uf_SH_moreDataSourcesTandbergCheckbox="//*[@id='moreDataSources']/table/tbody/tr[2]/td/div/table/tbody/tr[5]/td/input";
public String uf_SH_C3_CSOneRepoCheckboxResultPage="//*[@id='C3/CSOne/CARERepo']";
public String uf_SH_C3_CDETSCheckboxResultPage="//*[@id='CDETS/DDTSRepo']";
public String uf_SH_C3_NewsGroupCheckboxResultPage="//*[@id='NewsgroupRepo']";
public String uf_SH_C3_TechZoneCheckboxResultPage="//*[@id='Tech ZoneRepo']";
	public String uf_SH_C3_TandbergCheckboxResultPage="//*[@id='TandbergRepo']";
	public String uf_SH_C3_DynamicSoftCheckboxResultPage="//*[@id='DynamicsoftRepo']";
	public String uf_SH_C3_TandbergResultNoResultPage="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[2]/tbody/tr[6]/td[2]/span[2]";
	
	public String uf_SH_TS_TrainingRepoCheckboxResultPage="//*[@id='TS TrainingRepo']";
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////--------------FILTERS----------------////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String uf_SH_C3_allDateRangeFilters="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/div/div/table/tbody/tr[2]/td[2]/ul/li/span";
	public String uf_SH_C3_thisYearDateRangeFilters="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/div/div/table/tbody/tr[5]/td[2]/ul/li/span";
	public String uf_SH_C3_lastYearDateRangeFilters="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/div/div/table/tbody/tr[4]/td[2]/ul/li/span";
	public String uf_SH_C3_moreThanTwoYearsDateRangeFilters="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/div/div/table/tbody/tr[5]/td[2]/ul/li/span";
	public String uf_SH_C3_closeButtonDateRangeFilters="//*[@id='filterDiv']/div/table/tbody/tr/td/span[2]/img";
	public String uf_SH_C3_completeAreaFilters="//*[@id='filterDiv']";
	
	public String uf_SH_C3_LastMonthFilters="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/div/div/table/tbody/tr[6]/td[2]/ul/li/span";
	public String uf_SH_C3_BeforeLastYearFilters="//*[@id='filterDiv']/div[1]/table/tbody/tr[4]/td[2]/ul/li/span[1]";
	
//////////////////////////////////////-----------------SAVEDSEARCH----------------////////////////////////////////////////////////
public String uf_SH_savedSearch="//*[@id='resultsForm']/table/tbody/tr[9]/td/div/table[5]/tbody/tr/td/span";
public String uf_SH_sharedSearch="//*[@id='sharedSearch']/tbody/tr/td/span";
public String uf_SH_CiscoACSSavedSearch="//*[@id='savedSearchDiv']/table/tbody/tr";
public String uf_SH_RenamesavedSearch="//*[@id='resultsForm']/div[9]/ul/li[1]/a";
public String uf_SH_newNameForRenamesavedSearch="//*[@id='newName']";
public String uf_SH_RenameButtonPopUp="//*[@id='renameBtn']";
public String uf_SH_routerInSharedSearch="//*[@id='sharedSearchTable']/tbody/tr/td[2]/a";
public String Search_A_noOfResults= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[1]/td/table/tbody/tr/td[1]";
public String Search_A_ResultsTable= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr";
public String Search_A_displayedResults= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]";
public String Search_SH_newTabIcon= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/a[2]/img";
public String Cancel_SH_editDatasourcePopUp= "//*[@id='CancelDS']";
public String OK_SH_editDatasourcePopUp= "//*[@id='editDSdialog']/table/tbody/tr[2]/td/input[1]";

/////////////////******** SourceFire MetaFields ************//////////////////////////
public String uf_SH_Sourcefire_AssetSerialNumber="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[1]/td[2]/label[1]";
public String uf_SH_Sourcefire_BugID= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[1]/td[4]/label[1]";
public String uf_SH_Sourcefire_CaseNumber= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[1]/td[6]/label[1]";

public String uf_SH_Sourcefire_CaseOrigin= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[2]/label[1]";
public String uf_SH_Sourcefire_CaseType= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[4]/label[1]";
public String uf_SH_Sourcefire_Company= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[2]/td[6]/label[1]";

public String uf_SH_Sourcefire_CreatedDate="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[1]";
public String uf_SH_Sourcefire_CreatedDate_value="//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[2]/label[2]";
public String uf_SH_Sourcefire_IncidentResolvedDate= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[4]/label[1]";
public String uf_SH_Sourcefire_Owner= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[3]/td[6]/label[1]";

public String uf_SH_Sourcefire_ParentCaseId= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[4]/td[2]/label[1]";
public String uf_SH_Sourcefire_Priority= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[4]/td[4]/label[1]";
public String uf_SH_Sourcefire_Status= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/table/tbody/tr[4]/td[6]/label[1]";

public String uf_SH_CDETS_ExpandMoreFragment_link= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/a[4]";
public String uf_SH_CDETS_Data_ExpandMoreFragment_link= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[3]/table/tbody/tr[2]/td";
public String uf_SH_CDETS_CollapseAll_link= "//*[@id='resultsForm']/table/tbody/tr[9]/td[2]/table/tbody/tr[3]/td[1]/div[1]/a[4]";
}
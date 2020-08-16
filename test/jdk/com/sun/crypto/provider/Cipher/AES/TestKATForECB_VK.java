/*
 * Copyright (c) 2002, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 4635086
 * @library ../UTIL
 * @build TestUtil
 * @run main TestKATForECB_VK
 * @summary Known Answer Test for AES cipher with ECB mode
 * @author Valerie Peng
 */
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.math.*;

import java.util.*;

public class TestKATForECB_VK
{
    private static final String ALGO = "AES";
    private static final String MODE = "ECB";
    private static final String PADDING = "NoPadding";

    //ecb-vk.txt
    private static byte[] PT = new byte[16];

    private static String[][] CTS = {
        // Cipher Texts for 128-bit key
        {
"0EDD33D3C621E546455BD8BA1418BEC8",
"C0CC0C5DA5BD63ACD44A80774FAD5222",
"2F0B4B71BC77851B9CA56D42EB8FF080",
"6B1E2FFFE8A114009D8FE22F6DB5F876",
"9AA042C315F94CBB97B62202F83358F5",
"DBE01DE67E346A800C4C4B4880311DE4",
"C117D2238D53836ACD92DDCDB85D6A21",
"DC0ED85DF9611ABB7249CDD168C5467E",
"807D678FFF1F56FA92DE3381904842F2",
"0E53B3FCAD8E4B130EF73AEB957FB402",
"969FFD3B7C35439417E7BDE923035D65",
"A99B512C19CA56070491166A1503BF15",
"6E9985252126EE344D26AE369D2327E3",
"B85F4809F904C275491FCDCD1610387E",
"ED365B8D7D20C1F5D53FB94DD211DF7B",
"B3A575E86A8DB4A7135D604C43304896",
"89704BCB8E69F846259EB0ACCBC7F8A2",
"C56EE7C92197861F10D7A92B90882055",
"92F296F6846E0EAF9422A5A24A08B069",
"E67E32BB8F11DEB8699318BEE9E91A60",
"B08EEF85EAF626DD91B65C4C3A97D92B",
"661083A6ADDCE79BB4E0859AB5538013",
"55DFE2941E0EB10AFC0B333BD34DE1FE",
"6BFE5945E715C9662609770F8846087A",
"79848E9C30C2F8CDA8B325F7FED2B139",
"7A713A53B99FEF34AC04DEEF80965BD0",
"18144A2B46620D32C3C32CE52D49257F",
"872E827C70887C80749F7B8BB1847C7E",
"6B86C6A4FE6A60C59B1A3102F8DE49F3",
"9848BB3DFDF6F532F094679A4C231A20",
"925AD528E852E329B2091CD3F1C2BCEE",
"80DF436544B0DD596722E46792A40CD8",
"525DAF18F93E83E1E74BBBDDE4263BBA",
"F65C9D2EE485D24701FFA3313B9D5BE6",
"E4FC8D8BCA06425BDF94AFA40FCC14BA",
"A53F0A5CA1E4E6440BB975FF320DE6F8",
"D55313B9394080462E87E02899B553F0",
"34A71D761F71BCD344384C7F97D27906",
"233F3D819599612EBC89580245C996A8",
"B4F1374E5268DBCB676E447529E53F89",
"0816BD27861D2BA891D1044E39951E96",
"F3BE9EA3F10C73CA64FDE5DB13A951D1",
"2448086A8106FBD03048DDF857D3F1C8",
"670756E65BEC8B68F03D77CDCDCE7B91",
"EF968CF0D36FD6C6EFFD225F6FB44CA9",
"2E8767157922E3826DDCEC1B0CC1E105",
"78CE7EEC670E45A967BAB17E26A1AD36",
"3C5CEE825655F098F6E81A2F417DA3FB",
"67BFDB431DCE1292200BC6F5207ADB12",
"7540FD38E447C0779228548747843A6F",
"B85E513301F8A936EA9EC8A21A85B5E6",
"04C67DBF16C11427D507A455DE2C9BC5",
"03F75EB8959E55079CFFB4FF149A37B6",
"74550287F666C63BB9BC7838433434B0",
"7D537200195EBC3AEFD1EAAB1C385221",
"CE24E4D40C68A82B535CBD3C8E21652A",
"AB20072405AA8FC40265C6F1F3DC8BC0",
"6CFD2CF688F566B093F67B9B3839E80A",
"BD95977E6B7239D407A012C5544BF584",
"DF9C0130AC77E7C72C997F587B46DBE0",
"E7F1B82CADC53A648798945B34EFEFF2",
"932C6DBF69255CF13EDCDB72233ACEA3",
"5C76002BC7206560EFE550C80B8F12CC",
"F6B7BDD1CAEEBAB574683893C4475484",
"A920E37CC6DC6B31DA8C0169569F5034",
"919380ECD9C778BC513148B0C28D65FD",
"EE67308DD3F2D9E6C2170755E5784BE1",
"3CC73E53B85609023A05E149B223AE09",
"983E8AF7CF05EBB28D71EB841C9406E6",
"0F3099B2D31FA5299EE5BF43193287FC",
"B763D84F38C27FE6931DCEB6715D4DB6",
"5AE3C9B0E3CC29C0C61565CD01F8A248",
"F58083572CD90981958565D48D2DEE25",
"7E6255EEF8F70C0EF10337AAB1CCCEF8",
"AAD4BAC34DB22821841CE2F631961902",
"D7431C0409BB1441BA9C6858DC7D4E81",
"EF9298C65E339F6E801A59C626456993",
"53FE29F68FF541ABC3F0EF3350B72F7E",
"F6BBA5C10DB02529E2C2DA3FB582CC14",
"E4239AA37FC531A386DAD1126FC0E9CD",
"8F7758F857D15BBE7BFD0E416404C365",
"D273EB57C687BCD1B4EA7218A509E7B8",
"65D64F8D76E8B3423FA25C4EB58A210A",
"623D802B4EC450D66A16625702FCDBE0",
"7496460CB28E5791BAEAF9B68FB00022",
"34EA600F18BB0694B41681A49D510C1D",
"5F8FF0D47D5766D29B5D6E8F46423BD8",
"225F9286C5928BF09F84D3F93F541959",
"B21E90D25DF383416A5F072CEBEB1FFB",
"4AEFCDA089318125453EB9E8EB5E492E",
"4D3E75C6CD40EC4869BC85158591ADB8",
"63A8B904405436A1B99D7751866771B7",
"64F0DAAE47529199792EAE172BA53293",
"C3EEF84BEA18225D515A8C852A9047EE",
"A44AC422B47D47B81AF73B3E9AC9596E",
"D16E04A8FBC435094F8D53ADF25F5084",
"EF13DC34BAB03E124EEAD8B6BF44B532",
"D94799075C24DCC067AF0D392049250D",
"14F431771EDDCE4764C21A2254B5E3C8",
"7039329F36F2ED682B02991F28D64679",
"124EE24EDE5551639DB8B8B941F6141D",
"C2852879A34D5184E478EC918B993FEE",
"86A806A3525B93E432053C9AB5ABBEDF",
"C1609BF5A4F07E37C17A36366EC23ECC",
"7E81E7CB92159A51FFCEA331B1E8EA53",
"37A7BE002856C5A59A6E03EAFCE7729A",
"BDF98A5A4F91E890C9A1D1E5FAAB138F",
"4E96ACB66E051F2BC739CC3D3E34A26B",
"EE996CDD120EB86E21ECFA49E8E1FCF1",
"61B9E6B579DBF6070C351A1440DD85FF",
"AC369E484316440B40DFC83AA96E28E7",
"0A2D16DE985C76D45C579C1159413BBE",
"DA3FDC38DA1D374FA4802CDA1A1C6B0F",
"B842523D4C41C2211AFE43A5800ADCE3",
"9E2CDA90D8E992DBA6C73D8229567192",
"D49583B781D9E20F5BE101415957FC49",
"EF09DA5C12B376E458B9B8670032498E",
"A96BE0463DA774461A5E1D5A9DD1AC10",
"32CEE3341060790D2D4B1362EF397090",
"21CEA416A3D3359D2C4D58FB6A035F06",
"172AEAB3D507678ECAF455C12587ADB7",
"B6F897941EF8EBFF9FE80A567EF38478",
"A9723259D94A7DC662FB0C782CA3F1DD",
"2F91C984B9A4839F30001B9F430493B4",
"0472406345A610B048CB99EE0EF3FA0F",
"F5F39086646F8C05ED16EFA4B617957C",
"26D50F485A30408D5AF47A5736292450",
"0545AAD56DA2A97C3663D1432A3D1C84"
        },
        // Cipher Texts for 192-bit key
        {
"DE885DC87F5A92594082D02CC1E1B42C",
"C749194F94673F9DD2AA1932849630C1",
"0CEF643313912934D310297B90F56ECC",
"C4495D39D4A553B225FBA02A7B1B87E1",
"636D10B1A0BCAB541D680A7970ADC830",
"07CF045786BD6AFCC147D99E45A901A7",
"6A8E3F425A7599348F95398448827976",
"5518276836148A00D91089A20D8BFF57",
"F267E07B5E87E3BC20B969C61D4FCB06",
"5A1CDE69571D401BFCD20DEBADA2212C",
"70A9057263254701D12ADD7D74CD509E",
"35713A7E108031279388A33A0FE2E190",
"E74EDE82B1254714F0C7B4B243108655",
"39272E3100FAA37B55B862320D1B3EB3",
"6D6E24C659FC5AEF712F77BCA19C9DD0",
"76D18212F972370D3CC2C6C372C6CF2F",
"B21A1F0BAE39E55C7594ED570A7783EA",
"77DE202111895AC48DD1C974B358B458",
"67810B311969012AAF7B504FFAF39FD1",
"C22EA2344D3E9417A6BA07843E713AEA",
"C79CAF4B97BEE0BD0630AB354539D653",
"135FD1AF761D9AE23DF4AA6B86760DB4",
"D4659D0B06ACD4D56AB8D11A16FD83B9",
"F7D270028FC188E4E4F35A4AAA25D4D4",
"345CAE5A8C9620A9913D5473985852FF",
"4E8980ADDE60B0E42C0B287FEA41E729",
"F11B6D74E1F15155633DC39743C1A527",
"9C87916C0180064F9D3179C6F5DD8C35",
"71AB186BCAEA518E461D4F7FAD230E6A",
"C4A31BBC3DAAF742F9141C2A5001A49C",
"E7C47B7B1D40F182A8928C8A55671D07",
"8E17F294B28FA373C6249538868A7EEF",
"754404096A5CBC08AF09491BE249141A",
"101CB56E55F05D86369B6D1069204F0A",
"73F19BB6604205C6EE227B9759791E41",
"6270C0028F0D136C37A56B2CB64D24D6",
"A3BF7C2C38D1114A087ECF212E694346",
"49CABFF2CEF7D9F95F5EFB1F7A1A7DDE",
"EC7F8A47CC59B849469255AD49F62752",
"68FAE55A13EFAF9B07B3552A8A0DC9D1",
"211E6B19C69FAEF481F64F24099CDA65",
"DBB918C75BC5732416F79FB0C8EE4C5C",
"98D494E5D963A6C8B92536D3EC35E3FD",
"C9A873404D403D6F074190851D67781A",
"073AEF4A7C77D921928CB0DD9D27CAE7",
"89BDE25CEE36FDE769A10E52298CF90F",
"26D0842D37EAD38557C65E0A5E5F122E",
"F8294BA375AF46B3F22905BBAFFAB107",
"2AD63EB4D0D43813B979CF72B35BDB94",
"7710C171EE0F4EFA39BE4C995180181D",
"C0CB2B40DBA7BE8C0698FAE1E4B80FF8",
"97970E505194622FD955CA1B80B784E9",
"7CB1824B29F850900DF2CAD9CF04C1CF",
"FDF4F036BB988E42F2F62DE63FE19A64",
"08908CFE2C82606B2C15DF61B75CF3E2",
"B3AA689EF2D07FF365ACB9ADBA2AF07A",
"F2672CD8EAA3B98776660D0263656F5C",
"5BDEAC00E986687B9E1D94A0DA7BF452",
"E6D57BD66EA1627363EE0C4B711B0B21",
"03730DD6ACB4AD9996A63BE7765EC06F",
"A470E361AA5437B2BE8586D2F78DE582",
"7567FEEFA559911FD479670246B484E3",
"29829DEA15A4E7A4C049045E7B106E29",
"A407834C3D89D48A2CB7A152208FA4ED",
"68F948053F78FEF0D8F9FE7EF3A89819",
"B605174CAB13AD8FE3B20DA3AE7B0234",
"CCAB8F0AEBFF032893996D383CBFDBFA",
"AF14BB8428C9730B7DC17B6C1CBEBCC8",
"5A41A21332040877EB7B89E8E80D19FE",
"AC1BA52EFCDDE368B1596F2F0AD893A0",
"41B890E31B9045E6ECDC1BC3F2DB9BCC",
"4D54A549728E55B19A23660424A0F146",
"A917581F41C47C7DDCFFD5285E2D6A61",
"604DF24BA6099B93A7405A524D764FCB",
"78D9D156F28B190E232D1B7AE7FC730A",
"5A12C39E442CD7F27B3CD77F5D029582",
"FF2BF2F47CF7B0F28EE25AF95DBF790D",
"1863BB7D193BDA39DF090659EB8AE48B",
"38178F2FB4CFCF31E87E1ABCDC023EB5",
"F5B13DC690CC0D541C6BA533023DC8C9",
"48EC05238D7375D126DC9D08884D4827",
"ACD0D81139691B310B92A6E377BACC87",
"9A4AA43578B55CE9CC178F0D2E162C79",
"08AD94BC737DB3C87D49B9E01B720D81",
"3BCFB2D5D210E8332900C5991D551A2A",
"C5F0C6B9397ACB29635CE1A0DA2D8D96",
"844A29EFC693E2FA9900F87FBF5DCD5F",
"5126A1C41051FEA158BE41200E1EA59D",
"302123CA7B4F46D667FFFB0EB6AA7703",
"A9D16BCE7DB5C024277709EE2A88D91A",
"F013C5EC123A26CFC34B598C992A996B",
"E38A825CD971A1D2E56FB1DBA248F2A8",
"6E701773C0311E0BD4C5A097406D22B3",
"754262CEF0C64BE4C3E67C35ABE439F7",
"C9C2D4C47DF7D55CFA0EE5F1FE5070F4",
"6AB4BEA85B172573D8BD2D5F4329F13D",
"11F03EF28E2CC9AE5165C587F7396C8C",
"0682F2EB1A68BAC7949922C630DD27FA",
"ABB0FEC0413D659AFE8E3DCF6BA873BB",
"FE86A32E19F805D6569B2EFADD9C92AA",
"E434E472275D1837D3D717F2EECC88C3",
"74E57DCD12A21D26EF8ADAFA5E60469A",
"C275429D6DAD45DDD423FA63C816A9C1",
"7F6EC1A9AE729E86F7744AED4B8F4F07",
"48B5A71AB9292BD4F9E608EF102636B2",
"076FB95D5F536C78CBED3181BCCF3CF1",
"BFA76BEA1E684FD3BF9256119EE0BC0F",
"7D395923D56577F3FF8670998F8C4A71",
"BA02C986E529AC18A882C34BA389625F",
"3DFCF2D882AFE75D3A191193013A84B5",
"FAD1FDE1D0241784B63080D2C74D236C",
"7D6C80D39E41F007A14FB9CD2B2C15CD",
"7975F401FC10637BB33EA2DB058FF6EC",
"657983865C55A818F02B7FCD52ED7E99",
"B32BEB1776F9827FF4C3AC9997E84B20",
"2AE2C7C374F0A41E3D46DBC3E66BB59F",
"4D835E4ABDD4BDC6B88316A6E931A07F",
"E07EFABFF1C353F7384EBB87B435A3F3",
"ED3088DC3FAF89AD87B4356FF1BB09C2",
"4324D01140C156FC898C2E32BA03FB05",
"BE15D016FACB5BAFBC24FA9289132166",
"AC9B7048EDB1ACF4D97A5B0B3F50884B",
"448BECE1F86C7845DFA9A4BB2A016FB3",
"10DD445E87686EB46EA9B1ABC49257F0",
"B7FCCF7659FA756D4B7303EEA6C07458",
"289117115CA3513BAA7640B1004872C2",
"57CB42F7EE7186051F50B93FFA7B35BF",
"F2741BFBFB81663B9136802FB9C3126A",
"E32DDDC5C7398C096E3BD535B31DB5CE",
"81D3C204E608AF9CC713EAEBCB72433F",
"D4DEEF4BFC36AAA579496E6935F8F98E",
"C356DB082B97802B038571C392C5C8F6",
"A3919ECD4861845F2527B77F06AC6A4E",
"A53858E17A2F802A20E40D44494FFDA0",
"5D989E122B78C758921EDBEEB827F0C0",
"4B1C0C8F9E7830CC3C4BE7BD226FA8DE",
"82C40C5FD897FBCA7B899C70713573A1",
"ED13EE2D45E00F75CCDB51EA8E3E36AD",
"F121799EEFE8432423176A3CCF6462BB",
"4FA0C06F07997E98271DD86F7B355C50",
"849EB364B4E81D058649DC5B1BF029B9",
"F48F9E0DE8DE7AD944A207809335D9B1",
"E59E9205B5A81A4FD26DFCF308966022",
"3A91A1BE14AAE9ED700BDF9D70018804",
"8ABAD78DCB79A48D79070E7DA89664EC",
"B68377D98AAE6044938A7457F6C649D9",
"E4E1275C42F5F1B63D662C099D6CE33D",
"7DEF32A34C6BE668F17DA1BB193B06EF",
"78B6000CC3D30CB3A74B68D0EDBD2B53",
"0A47531DE88DD8AE5C23EAE4F7D1F2D5",
"667B24E8000CF68231EC484581D922E5",
"39DAA5EBD4AACAE130E9C33236C52024",
"E3C88760B3CB21360668A63E55BB45D1",
"F131EE903C1CDB49D416866FD5D8DE51",
"7A1916135B0447CF4033FC13047A583A",
"F7D55FB27991143DCDFA90DDF0424FCB",
"EA93E7D1CA1111DBD8F7EC111A848C0C",
"2A689E39DFD3CBCBE221326E95888779",
"C1CE399CA762318AC2C40D1928B4C57D",
"D43FB6F2B2879C8BFAF0092DA2CA63ED",
"224563E617158DF97650AF5D130E78A5",
"6562FDF6833B7C4F7484AE6EBCC243DD",
"93D58BA7BED22615D661D002885A7457",
"9A0EF559003AD9E52D3E09ED3C1D3320",
"96BAF5A7DC6F3DD27EB4C717A85D261C",
"B8762E06884900E8452293190E19CCDB",
"785416A22BD63CBABF4B1789355197D3",
"A0D20CE1489BAA69A3612DCE90F7ABF6",
"700244E93DC94230CC607FFBA0E48F32",
"85329E476829F872A2B4A7E59F91FF2D",
"E4219B4935D988DB719B8B8B2B53D247",
"6ACDD04FD13D4DB4409FE8DD13FD737B",
"9EB7A670AB59E15BE582378701C1EC14",
"29DF2D6935FE657763BC7A9F22D3D492",
"99303359D4A13AFDBE6C784028CE533A",
"FF5C70A6334545F33B9DBF7BEA0417CA",
"289F58A17E4C50EDA4269EFB3DF55815",
"EA35DCB416E9E1C2861D1682F062B5EB",
"3A47BF354BE775383C50B0C0A83E3A58",
"BF6C1DC069FB95D05D43B01D8206D66B",
"046D1D580D5898DA6595F32FD1F0C33D",
"5F57803B7B82A110F7E9855D6A546082",
"25336ECF34E7BE97862CDFF715FF05A8",
"ACBAA2A943D8078022D693890E8C4FEF",
"3947597879F6B58E4E2F0DF825A83A38",
"4EB8CC3335496130655BF3CA570A4FC0",
"BBDA7769AD1FDA425E18332D97868824",
"5E7532D22DDB0829A29C868198397154",
"E66DA67B630AB7AE3E682855E1A1698E",
"4D93800F671B48559A64D1EA030A590A",
"F33159FCC7D9AE30C062CD3B322AC764",
"8BAE4EFB70D33A9792EEA9BE70889D72"
        },
        // Cipher Texts for 256-bit key
        {
"E35A6DCB19B201A01EBCFA8AA22B5759",
"5075C2405B76F22F553488CAE47CE90B",
"49DF95D844A0145A7DE01C91793302D3",
"E7396D778E940B8418A86120E5F421FE",
"05F535C36FCEDE4657BE37F4087DB1EF",
"D0C1DDDD10DA777C68AB36AF51F2C204",
"1C55FB811B5C6464C4E5DE1535A75514",
"52917F3AE957D5230D3A2AF57C7B5A71",
"C6E3D5501752DD5E9AEF086D6B45D705",
"A24A9C7AF1D9B1E17E1C9A3E711B3FA7",
"B881ECA724A6D43DBC6B96F6F59A0D20",
"EC524D9A24DFFF2A9639879B83B8E137",
"34C4F345F5466215A037F443635D6F75",
"5BA5055BEDB8895F672E29F2EB5A355D",
"B3F692AA3A435259EBBEF9B51AD1E08D",
"414FEB4376F2C64A5D2FBB2ED531BA7D",
"A20D519E3BCA3303F07E81719F61605E",
"A08D10E520AF811F45BD60A2DC0DC4B1",
"B06893A8C563C430E6F3858826EFBBE4",
"0FFEE26AE2D3929C6BD9C6BEDFF84409",
"4D0F5E906ED77801FC0EF53EDC5F9E2B",
"8B6EC00119AD8B026DCE56EA7DEFE930",
"69026591D43363EE9D83B5007F0B484E",
"27135D86950C6A2F86872706279A4761",
"35E6DB8723F281DA410C3AC8535ED77C",
"57427CF214B8C28E4BBF487CCB8D0E09",
"6DF01BF56E5131AC87F96E99CAB86367",
"3856C5B55790B768BBF7D43031579BCF",
"1E6ED8FB7C15BC4D2F63BA7037ED44D0",
"E1B2ED6CD8D93D455534E401156D4BCF",
"EFBCCA5BDFDAD10E875F02336212CE36",
"0B777F02FD18DCE2646DCFE868DFAFAD",
"C8A104B5693D1B14F5BF1F10100BF508",
"4CCE6615244AFCB38408FECE219962EA",
"F99E7845D3A255B394C9C050CBA258B1",
"B4AFBB787F9BCFB7B55FDF447F611295",
"AE1C426A697FAF2808B7EF6ADDB5C020",
"7572F92811A85B9BDD38DEAD9945BCAE",
"71BC7AA46E43FB95A181527D9F6A360F",
"5542EF2923066F1EC8F546DD0D8E7CA8",
"6B92317C7D623790B748FDD7EFC42422",
"0FE7C097E899C71EF045360F8D6C25CF",
"4ECE7EE107D0264D04693151C25B9DF6",
"FD6AE687CBFCA9E301045888D3BB9605",
"476B579C8556C7254424902CC1D6D36E",
"4133CBCDFDD6B8860A1FC18665D6D71B",
"3B36EC2664798C108B816812C65DFDC7",
"364E20A234FEA385D48DC5A09C9E70CF",
"4A4BA25969DE3F5EE5642C71AAD0EFD1",
"E42CBAAE43297F67A76C1C501BB79E36",
"23CEDEDA4C15B4C037E8C61492217937",
"A1719147A1F4A1A1180BD16E8593DCDE",
"AB82337E9FB0EC60D1F25A1D0014192C",
"74BF2D8FC5A8388DF1A3A4D7D33FC164",
"D5B493317E6FBC6FFFD664B3C491368A",
"BA767381586DA56A2A8D503D5F7ADA0B",
"E8E6BC57DFE9CCADB0DECABF4E5CF91F",
"3C8E5A5CDC9CEED90815D1F84BB2998C",
"283843020BA38F056001B2FD585F7CC9",
"D8ADC7426F623ECE8741A70621D28870",
"D7C5C215592D06F00E6A80DA69A28EA9",
"52CF6FA433C3C870CAC70190358F7F16",
"F63D442A584DA71786ADEC9F3346DF75",
"549078F4B0CA7079B45F9A5ADAFAFD99",
"F2A5986EE4E9984BE2BAFB79EA8152FA",
"8A74535017B4DB2776668A1FAE64384C",
"E613342F57A97FD95DC088711A5D0ECD",
"3FFAEBF6B22CF1DC82AE17CD48175B01",
"BAFD52EFA15C248CCBF9757735E6B1CE",
"7AF94BC018D9DDD4539D2DD1C6F4000F",
"FE177AD61CA0FDB281086FBA8FE76803",
"74DBEA15E2E9285BAD163D7D534251B6",
"23DD21331B3A92F200FE56FF050FFE74",
"A69C5AA34AB20A858CAFA766EACED6D8",
"3F72BB4DF2A4F941A4A09CB78F04B97A",
"72CC43577E1FD5FD14622D24D97FCDCC",
"D83AF8EBE93E0B6B99CAFADE224937D1",
"44042329128D56CAA8D084C8BD769D1E",
"14102D72290DE4F2C430ADD1ED64BA1D",
"449124097B1ECD0AE7065206DF06F03C",
"D060A99F8CC153A42E11E5F97BD7584A",
"65605B3EA9261488D53E48602ADEA299",
"C5E5CAD7A208DE8EA6BE049EFE5C7346",
"4C280C46D2181646048DD5BC0C0831A5",
"5DD65CF37F2A0929559AABAFDA08E730",
"31F2335CAAF264172F69A693225E6D22",
"3E28B35F99A72662590DA96426DD377F",
"570F40F5D7B20441486578ED344343BE",
"C54308AD1C9E3B19F8B7417873045A8C",
"CBF335E39CE13ADE2B696179E8FD0CE1",
"9C2FBF422355D8293083D51F4A3C18A9",
"5ED8B5A31ECEFAB16C9AA6986DA67BCE",
"627815DCFC814ABC75900041B1DD7B59",
"9EF3E82A50A59F166260494F7A7F2CC3",
"878CD0D8D920888B5935D6C351128737",
"E44429474D6FC3084EB2A6B8B46AF754",
"EBAACF9641D54E1FB18D0A2BE4F19BE5",
"13B3BF497CEE780E123C7E193DEA3A01",
"6E8F381DE00A41161F0DF03B4155BFD4",
"35E4F29BBA2BAE01144910783C3FEF49",
"55B17BD66788CEAC366398A31F289FFB",
"11341F56C0D6D1008D28741DAA7679CE",
"4DF7253DF421D83358BDBE924745D98C",
"BAE2EE651116D93EDC8E83B5F3347BE1",
"F9721ABD06709157183AF3965A659D9D",
"19A1C252A613FE2860A4AE6D75CE6FA3",
"B5DDB2F5D9752C949FBDE3FFF5556C6E",
"81B044FCFFC78ECCFCD171AAD0405C66",
"C640566D3C06020EB2C42F1D62E56A9B",
"EA6C4BCF425291679FDFFD26A424FBCC",
"57F6901465D9440D9F15EE2CBA5A4090",
"FBCFA74CADC7406260F63D96C8AAB6B1",
"DFF4F096CEA211D4BBDACA033D0EC7D1",
"1EE5190D551F0F42F675227A381296A9",
"F98E1905012E580F097623C10B93054F",
"E7D43743D21DD3C9F168C86856558B9A",
"632A9DDA730DAB67593C5D08D8AC1059",
"E084317000715B9057BC9DE9F3AB6124",
"61F9EF33A0BB4E666C2ED99101919FAB",
"6DC1D68A11834657D46703C22578D59A",
"53AC1548863D3D16F1D4DC7242E05F2C",
"E82CD587A408306AD78CEAE0916B9F8C",
"0FD2D40EA6AD17A3A767F0A8600D6295",
"AD84CC8255ADB39DFCA23F92761AE7E9",
"F4F20CF7D51BEE7DA024A2B11A7ECA0B",
"5057691B85D9CE93A193214DB0A016B6",
"0F58C960876390BDEF4BB6BE95CAA1EE",
"9A3E66EEBC21BC0BD9430B341EF465FA",
"20415035F34B8BCBCB28ABF07F78F0D4",
"AC89FC7BA10479EBF10DE65BCEF89B3C",
"068FA75A30BE443171AF3F6FEB1A20D2",
"50E02F213246C525A8C27700CA34B502",
"227DA47D5A0906DB3AB042BB0A695FB6",
"8663AC30ED12514F1DE46777F4514BFC",
"A987D4BC12E1DE9F4B6DF43567C34A8B",
"6D5A0370F599ACA605F63B04E5143D0C",
"9809266E378B07B7AFDB3BAA97B7E442",
"8F753252B30CCCACE12D9A301F4D5090",
"032465F6C0CE34D41962F561692A1AFF",
"C50E9AD5BEB8F3B00821DD47FF8AC093",
"9C6FEA3D46268D54A6829B2AD25BB276",
"0FD8575E87706F561343D7B3A41E044A",
"BEE9BEB3739540D88CBCE77925F0A114",
"D24EAEE7FFFBAC3D6F26C2DCE0DCDE28",
"47771A90398FF0F7FA821C2F8F5E1398",
"4639741B6F84B135AD118C8249B64ED0",
"8EE5505EC85567697A3306F250A27720",
"7C8A19AC1AEFBC5E0119D91A5F05D4C2",
"5141B9B672E54773B672E3A6C424887B",
"B5A2D3CD206653C6402F34FB0AE3613D",
"0F5BD9408738231D114B0A82753279A3",
"FEF033FF4268EA487FC74C5E43A45338",
"A3EDC09DCD529B113910D904AD855581",
"AB8FBB6F27A0AC7C55B59FDD36B72F1C",
"EEA44D5ED4D769CC930CD83D8999EC46",
"6972276803AE9AA7C6F431AB10979C34",
"86DEAA9F39244101818178474D7DBDE9",
"88C6B466EA361D662D8D08CBF181F4FE",
"91AB2C6B7C63FF59F7CBEEBF91B20B95",
"2DFE6C146AD5B3D8C3C1718F13B48E01",
"C7CFF1623451711391A302EEC3584AAA",
"089FE845CC05011686C66019D18BE050",
"08C8410B9B427211A67124B0DCCEAD48",
"8D91592F5566085254784606334D7629",
"3298FEAAF2E1201D6299FF8846639C97",
"C497CB9F0BDFE0EFC8C2F3F90760AA72",
"2788AFD046E0309CBE4424690DA2AB89",
"E9891707F25EF29FEE372890D4258982",
"DB041D94A23D45D4D4DCED5A030CAF61",
"FFAFDBF0ECB18DF9EA02C27077448E6D",
"2DAAA42A7D0A1D3B0E4761D99CF2150A",
"3B7A54CB7CF30ABE263DD6ED5BFE8D63",
"EEFA090174C590C448A55D43648F534A",
"9E15798731ED42F43EA2740A691DA872",
"31FBD661540A5DEAAD1017CFD3909EC8",
"CDA9AE05F224140E28CB951721B44D6A",
"0C5BC512C60A1EAC3434EFB1A8FBB182",
"AA863610DEEEEB62D045E87EA30B59B5",
"6AC2448DE568D279C7EEBE1DF403920C",
"E2011E3D292B26888AE801215FD0CB40",
"E06F3E15EE3A61672D1C99BADE5B9DBE",
"BB7027F0548CF6712CEB4C7A4B28E178",
"061EC21FB70FADBDF87C3BD2AE23825B",
"4C21F26FE94ABBAC381352375314C3EB",
"F7CEE6DD99909C2B569EEDA61ED8942E",
"CE98C4A876C65E4CCB261EBB1D9DF7F5",
"A5491881CF833C3604ABC08044F402AC",
"A1BA16E64CCCB3087D57A768507B0BFC",
"D55951E202D2949EBD3BE43120C738BF",
"EBB8E43069E69F450EFEC65DCD52B7FD",
"2B292135663B4AA5ABFE9423D57E7EE9",
"E91BF974B3BE3AD966249D8655292A85",
"384365998EAA9562236CC58F6ADF9610",
"C2E997012AA3D4D8D359C9A947CBE69F",
"F49421204148BA213BE87E2D5C22B0BF",
"82ED0ED9953AA92E4DF30929CA65C00F",
"291EB1D11653C8479437C74A977F5106",
"BCB997B1939B8983ABD550D6025683E3",
"1FBA2592C6F489775CAADA71F9B983E9",
"969F66F217AF1A3DB9E41C1B29039824",
"A54BB7D6B17E423AC0A7744C19073CB8",
"B0AC6E6578D1021F47DCF9748A32EAD5",
"B87B361C3B7B194C77A4358D4669153E",
"46A133847F96EAA8282A799DC8899D58",
"2265EC3A9F2D5C9547A091CC8CFB18EA",
"54CBF3A6FC4FE56D426117AA1FFD1DDE",
"5312877CCEAB6CFB0905394A370A8003",
"7190BD6EC613FE38B84ECFE28F702FE4",
"D1FA5B9CA89A43B04C05F0EF29EF68CD",
"808285751548ED934FD1056D2D9AE8BA",
"2758DEF3E7B95A9AE89777BE64D5A6CF",
"07D81F87DB3E0ACC82B01E08FB22F3C1",
"8DA250E5553D650711A75EE1CB4FD1C7",
"A93D946BD0E87F32719DF5F158CEE669",
"03945236EC2A4D4EAF30B8ABEB54330D",
"11CC35301F24B79DDE31AEA2D1354F88",
"E73715B3E8D9A290F44AE6FFBF247E5D",
"7345E07732B71CB158BBF64CCA5C5B96",
"6E128F296D24705A1924FD9B70C4ED04",
"95A789776F036783FBD330947083F54F",
"360DEC2533EA4AA2E3E54FD3DE2906EB",
"E68EFD7FECF4D601EA22727BD764965B",
"9065C64A8BFF44AC33EDBB611CF83D7B",
"8F33C8DF2A7A51CE8090E8F123BC3723",
"807F391FFBA8291BA625623210F99018",
"5E8B3F3A701522CE5CAA761C929D6292",
"3BA404DC38735A78289E3809E8364835",
"D23BEDBAD229F8305DC425B6B759DCC9",
"44880F21CF5913040AE376AEE2A10AD8",
"9BC98E29D057C0E828C3B5CCE69256C1",
"B293CC7A975DA141A68279368057CC41",
"8D60FB87ACD91385B313BE5F1D7BD30F",
"2C8E56132D70291B303C48FDF75543CD",
"D1F80035B826791F6CE4E59B7DB1BB0D",
"42CE6224FC36469339A133DD08173BD4",
"61817155EA41BCBA2AF7F06AE7CBF585",
"D1923A9866068D2EF5FB77D57C3315B6",
"B37CBDB5D719F49691CA968EF2E84140",
"EC974E653A055D7F8F22171030F68E1D",
"DDE5D3B9AAD9C32213BB3675A822499C",
"D3B6E9216EA1AE57EB1C628A3C38AB78",
"82C99ECC69472B7E96324B042AE8B87A",
"97144DC5338C43600F84439C0AA0D147",
"400AC4A0BBADA1DB2121EB144C7E5209",
"EFD9D550EB419ED278F4885A490AB54C",
"2AB7816E149B7C0404C88A8857793670",
"5B591DFF9E8DEE15BAD24C025DBCA481",
"0C06633E30721C3749F49AD8CBF2B754",
"96D6D31A41B5123B2035FD91A921D4CA",
"E7F6C34D86668BC2805CA7793C5E86AD",
"F46DFF5FF500D6879C4D3E45CF0CF0F3",
"60D842D9C61DA7495C116197B7CECBBE",
"D45B24EDB673353EBDF248B8FA06B67A",
"119EAEBCC165D0BD02C0D35DC82EF992",
"E673143680414ADA301D0ED34626B9FE",
"6B6CFE160A6263631B292F879EEFF926"
        }
    };

    private static int[] KEY_SIZES = {
        16, 24, 32
    };

    /**
     * Constructs an AES Key according to the specified key size and
     * round number.
     * @param len key size in bytes, i.e. 16, 24, or 32
     * @param rounds round number starting from 0, i.e. valid from 0 to len-1.
     */
    private static SecretKey constructAESKey(int len, int rounds)
        throws IllegalArgumentException {
        if ((len != 16) && (len != 24) && (len != 32)) {
            throw new IllegalArgumentException("Wrong Key Length: " + len);
        }
        byte[] rawKeyValue = constructKeyValue(len, rounds);
        SecretKeySpec key = new SecretKeySpec(rawKeyValue, "AES");
        return key;
    }

    private static byte[] constructKeyValue(int keysize, int rounds) {
        byte[] tempKeyValue = new byte[keysize];
        Arrays.fill(tempKeyValue, (byte)0);

        int whichByte = rounds/8;
        int whichDigit = rounds % 8;
        if ((whichByte >= keysize) || (whichDigit < 0) ||
            (whichDigit > 8)) {
            throw new IllegalArgumentException("Invalid keysize/rounds " +
                                               "combination :" + keysize +
                                               "/" + rounds);
        }
        switch (whichDigit) {
        case 0:
            tempKeyValue[whichByte] = (byte)0x80;
            break;
        case 1:
            tempKeyValue[whichByte] = (byte)0x40;
            break;
        case 2:
            tempKeyValue[whichByte] = (byte)0x20;
            break;
        case 3:
            tempKeyValue[whichByte] = (byte)0x10;
            break;
        case 4:
            tempKeyValue[whichByte] = (byte)0x08;
            break;
        case 5:
            tempKeyValue[whichByte] = (byte)0x04;
            break;
        case 6:
            tempKeyValue[whichByte] = (byte)0x02;
            break;
        case 7:
            tempKeyValue[whichByte] = (byte)0x01;
            break;
        }
        return tempKeyValue;
    }

    private static byte[] constructByteArray(String s) {
        int len = s.length()/2;
        byte[] tempValue = new byte[len];
        for (int i = 0; i < len; i++) {
            tempValue[i] = Integer.valueOf(s.substring(2*i, 2*i+2),
                                           16).byteValue();
        }
        return tempValue;
    }

    public boolean execute() throws Exception {
        String transformation = ALGO+"/"+MODE+"/"+PADDING;
        Cipher c = Cipher.getInstance(transformation, "SunJCE");

        for (int i=0; i<KEY_SIZES.length; i++) {
            if (KEY_SIZES[i]*8 >
                Cipher.getMaxAllowedKeyLength(transformation)) {
                // skip if this key length is larger than what's
                // configured in the jce jurisdiction policy files
                continue;
            }
            int rounds = KEY_SIZES[i] * 8;
            byte[] plainText = PT;
            byte[] cipherText = null;
            try {
            for (int j=0; j < rounds; j++) {
                SecretKey aesKey = constructAESKey(KEY_SIZES[i], j);
                c.init(Cipher.ENCRYPT_MODE, aesKey);
                cipherText = c.doFinal(plainText);
                byte[] answer = constructByteArray(CTS[i][j]);
                if (!Arrays.equals(cipherText, answer)) {
                    throw new Exception((i+1) + "th known answer test failed for encryption");
                }
                c.init(Cipher.DECRYPT_MODE, aesKey);
                byte[] restored = c.doFinal(cipherText);
                if (!Arrays.equals(plainText, restored)) {
                    throw new Exception((i+1) + "th known answer test failed for decryption");
                }
            }
            System.out.println("Finished KAT for " + KEY_SIZES[i] + "-byte key");
            } catch (SecurityException se) {
                TestUtil.handleSE(se);
            }
        }

        // passed all tests...hooray!
        return true;
    }

    public static void main (String[] args) throws Exception {
        TestKATForECB_VK test = new TestKATForECB_VK();
        String testName = test.getClass().getName() + "[" + ALGO +
            "/" + MODE + "/" + PADDING + "]";
        if (test.execute()) {
            System.out.println(testName + ": Passed!");
        }
    }
}

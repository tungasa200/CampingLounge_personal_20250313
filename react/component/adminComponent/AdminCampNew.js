import React, {useContext, useEffect, useState} from "react";
import axios from "axios";
import {HttpHeadersContext} from "../context/HttpHeadersProvider";

function AdminCampNew({isOpen, onClose}){
    const [ campName,setCampName] = useState("");
    const [campInfo ,setCampInfo] = useState("");
    const [campTel ,setCampTel] = useState("");
    const [campAddressDo ,setCampAddressDo] = useState("");
    const [ campAddressGungu,setCampAddressGungu] = useState("");
    const [campAddress1 ,setCampAddress1] = useState("");
    const [campAddress2 ,setCampAddress2] = useState("");
    const [campMapX ,setCampMapX] = useState("");
    const [campMapY ,setCampMapY] = useState("");
    const [toilet ,setToilet] = useState(0);
    const [hotWater ,setHotWater] = useState(false);
    const [ electric,setElectric] = useState(false);
    const [ fireWood,setFireWood] = useState(false);
    const [wifi ,setWifi] = useState(false);
    const [playGround ,setPlayGround] = useState(false);
    const [ pet,setPet] = useState(false);
    const [swimming ,setSwimming] = useState(false);
    const [totalCapacity ,setTotalCapacity] = useState(0);
    const [campHit ,setCampHit] = useState(0);
    const [campLike ,setCampLike] = useState(0);
    const [thumb, setThumb] = useState([]);
    const [images, setImages] = useState([]);

    const [campTel1,setCampTel1] = useState("010");
    const [campTel2,setCampTel2] = useState("");
    const [campTel3,setCampTel3] = useState("");
    const [toiletCheck, setToiletCheck] = useState(false);

    const token = localStorage.getItem("CL_access_token");
    const { headers, setHeaders } = useContext(HttpHeadersContext);

    const handleChangeThumb = (e) => {
        const selectedThumb = Array.from(e.target.files).slice(0, 1);
        setThumb((prevFiles) => [...prevFiles, ...selectedThumb]);
    };
    const handleChangeImages = (e) => {
        const selectedImages = Array.from(e.target.files).slice(0, 8);
        setImages((prevFiles) => [...prevFiles, ...selectedImages]);
    };

    const campTellSum = ()=>{
        setCampTel(`${campTel1} - ${campTel2} - ${campTel3}`);
    }
    const toiletCount = ()=>{
        setToilet(toiletCheck ? 1 : 0);
    }

    useEffect(() => {
        setHeaders({ "Authorization": `Bearer ${token}` });
    }, []);

    const campWrite = async (e) => {
        e.preventDefault();
        console.log("test")
        const req = {
            campName : campName,
            campInfo : campInfo,
            campTel : campTel,
            campAddressDo : campAddressDo,
            campAddressGungu :campAddressGungu,
            campAddress1 : campAddress1,
            campAddress2 : campAddress2,
            campMapX : campMapX,
            campMapY : campMapY,
            toilet : toilet,
            hotWater : hotWater,
            electric : electric,
            fireWood : fireWood,
            wifi : wifi,
            playGround : playGround,
            pet : pet,
            swimming : swimming,
            totalCapacity :totalCapacity,
            campHit : campHit,
            campLike : campLike,
        };

        const formData = new FormData();
        formData.append("campDto", JSON.stringify(req));
        thumb.forEach(file => formData.append("thumb", file));
        images.forEach(file => formData.append("images", file));
        console.log(req);
        console.log(formData);
        await axios
            .post("http://localhost:8080/admin/camp/write", formData, {
                headers: {
                    ...headers, // 기존 headers 값 유지
                    "Content-Type": "multipart/form-data" // 필요한 헤더 추가
                }
                })
            .then((resp) => {
                console.log("[AdminCampNew.js] CampWrite() success.");
                console.log(resp.data);
                alert("새로운 게시글을 성공적으로 등록했습니다.");
                onClose();
            })
            .catch((err) => {
                console.log("[AdminCampNew.js] CampWrite() error.");
                console.log(err);
                alert("게시물 등록 실패");
            });
    };

    return(
        <>
            <div id="dark-area" className={`${isOpen ? "active" : ""}`} onClick={onClose}></div>
            <article id="modal" className={`${isOpen ? "active" : ""}`}>
                <div className="close-btn" onClick={onClose}>
                    <img src="/images/common/icon_close.svg" alt="닫기"/>
                </div>
                <div className="modal_inner camp-write">
                    <h3 className="fs_xlg">캠핑장 등록</h3>
                    <form onSubmit={campWrite}>
                        <table>
                            <colgroup>
                                <col width={20 + "%"}/>
                                <col width={80 + "%"}/>
                            </colgroup>
                            <tbody>
                            <tr>
                                <td>
                                    <label className="label fs_md" htmlFor="campName">캠핑장 이름</label>
                                </td>
                                <td>
                                    <input className="max" type="text" id="campName" name="campName" value={campName}
                                           onChange={(e) => {
                                               setCampName(e.target.value);
                                           }} required/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md" htmlFor="campInfo">캠핑장 설명</label>
                                </td>
                                <td>
                                    <textarea rows={10} id="campInfo" name="campInfo" value={campInfo}
                                              onChange={(e) => {
                                                  setCampInfo(e.target.value);
                                              }} required/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">연락처</label>
                                </td>
                                <td style={{display: "flex", gap: "1rem"}}>
                                    <select value={campTel1} onChange={(e) => {
                                        setCampTel1(e.target.value);
                                        campTellSum();
                                    }}>
                                        <option>010</option>
                                        <option>011</option>
                                        <option>016</option>
                                        <option>019</option>
                                        <option>02</option>
                                        <option>031</option>
                                        <option>032</option>
                                        <option>033</option>
                                        <option>041</option>
                                        <option>042</option>
                                        <option>043</option>
                                        <option>044</option>
                                        <option>051</option>
                                        <option>052</option>
                                        <option>053</option>
                                        <option>054</option>
                                        <option>055</option>
                                        <option>061</option>
                                        <option>062</option>
                                        <option>063</option>
                                        <option>064</option>
                                    </select> -
                                    <input className="max" type="number" value={campTel2}
                                           onChange={(e) => {
                                               setCampTel2(e.target.value);
                                               campTellSum();
                                           }}/> -
                                    <input className="max" type="number" value={campTel3}
                                           onChange={(e) => {
                                               setCampTel3(e.target.value);
                                               campTellSum();
                                           }}/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">주소</label>
                                </td>
                                <td>
                                    <div className="item-box mb_sm" style={{gap: "1rem"}}>
                                        <input className="max" name="campAddressDo" type="text" placeholder="도"
                                               value={campAddressDo} onChange={(e) => {
                                            setCampAddressDo(e.target.value);
                                        }}/>
                                        <input className="max" type="text" name="campAddressGungu" placeholder="시/군/구"
                                               value={campAddressGungu} onChange={(e) => {
                                            setCampAddressGungu(e.target.value);
                                        }}/>
                                    </div>
                                    <input className="max mb_sm" name="campAddress1" type="text" placeholder="상세 주소 1"
                                           value={campAddress1} onChange={(e) => {
                                        setCampAddress1(e.target.value);
                                    }}/>
                                    <input className="max" type="text" name="campAddress2" placeholder="상세 주소 2"
                                           value={campAddress2} onChange={(e) => {
                                        setCampAddress2(e.target.value);
                                    }}/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">기타</label>
                                </td>
                                <td>
                                    <div className="item-box" style={{justifyContent: "space-between"}}>
                                        <input type="text" name="campMapX" placeholder="x 좌표" value={campMapX}
                                               onChange={(e) => {
                                                   setCampMapX(e.target.value);
                                               }}/> :
                                        <input type="text" name="campMapY" placeholder="y 좌표" value={campMapY}
                                               onChange={(e) => {
                                                   setCampMapY(e.target.value);
                                               }}/> <p className="tc-p">|</p>
                                        <input type="text" name="totalCapacity" placeholder="최대 수용 인원"
                                               value={totalCapacity} onChange={(e) => {
                                            setTotalCapacity(Number(e.target.value));
                                        }}/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">시설 정보</label>
                                </td>
                                <td>
                                    <ul className="icon_list">
                                        <li>
                                            <input id="electric" name="electric" type="checkbox" checked={electric}
                                                   onChange={(e) => {
                                                       setElectric(e.target.checked);
                                                   }}/>
                                            <label htmlFor="electric">전기</label>
                                        </li>
                                        <li>
                                            <input id="toilet" name="toilet" type="checkbox" checked={toiletCheck}
                                                   onChange={(e) => {
                                                       setToiletCheck(e.target.checked);
                                                       toiletCount();
                                                   }}/>
                                            <label htmlFor="toilet">화장실</label>
                                        </li>
                                        <li>
                                            <input id="hotWater" name="hotWater" type="checkbox" checked={hotWater}
                                                   onChange={(e) => {
                                                       setHotWater(e.target.checked);
                                                   }}/>
                                            <label htmlFor="hotWater">온수</label>
                                        </li>
                                        <li>
                                            <input id="fireWood" name="fireWood" type="checkbox" checked={fireWood}
                                                   onChange={(e) => {
                                                       setFireWood(e.target.checked);
                                                   }}/>
                                            <label htmlFor="fireWood">장작 판매</label>
                                        </li>
                                        <li>
                                            <input id="wifi" name="wifi" type="checkbox" checked={wifi}
                                                   onChange={(e) => {
                                                       setWifi(e.target.checked);
                                                   }}/>
                                            <label htmlFor="wifi">와이파이</label>
                                        </li>
                                        <li>
                                            <input id="playGround" name="playGround" type="checkbox"
                                                   checked={playGround} onChange={(e) => {
                                                setPlayGround(e.target.checked);
                                            }}/>
                                            <label htmlFor="playGround">운동 시설</label>
                                        </li>
                                        <li>
                                            <input id="pet" name="pet" type="checkbox" checked={pet} onChange={(e) => {
                                                setPet(e.target.checked);
                                            }}/>
                                            <label htmlFor="pet">동물 출입</label>
                                        </li>
                                        <li>
                                            <input id="swimming" name="swimming" type="checkbox" checked={swimming}
                                                   onChange={(e) => {
                                                       setSwimming(e.target.checked);
                                                   }}/>
                                            <label htmlFor="swimming">물놀이</label>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">썸네일</label>
                                </td>
                                <td>
                                    <input type="file" name="thumb" onChange={handleChangeThumb}/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label className="label fs_md">캠핑장 사진</label>
                                </td>
                                <td>
                                    <input type="file" multiple name="images" onChange={handleChangeImages}/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <button type="submit" className="btn btn-lg btn-s-f mt_md mlr-a"
                                style={{borderRadius: "15px"}}>등록
                        </button>
                    </form>
                    <div className="bar mt_lg"></div>
                </div>
            </article>
        </>
    );
}

export default AdminCampNew;
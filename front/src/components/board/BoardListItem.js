import styles from 'components/board/BoardListItem.module.css'


const BoardListItem = () => {
  return (
    <article className={styles.boardListItem}>
      <div className={styles.boardListItemTop}>
        <div className={styles.boardListItemProfileImageBox}>
          <img className={styles.boardListItemProfileImage} src={`https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMTgy/MDAxNjA0MjI4ODc1NDMw.Ex906Mv9nnPEZGCh4SREknadZvzMO8LyDzGOHMKPdwAg.ZAmE6pU5lhEdeOUsPdxg8-gOuZrq_ipJ5VhqaViubI4g.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%95%98%EB%8A%98%EC%83%89.jpg?type=w800`}></img>
        </div>
        <div className={styles.boardListItemInfoBox}>
          <h3 className={styles.boardListItemWriterNickname}>{"안녕하세요나는주코야키"}</h3>
          <time className={styles.boardListItemWriteCreateTime}>{"2022. 05. 12."}</time>
        </div>
      </div>
      <section className={styles.boardListItemContentBox}>
        <h2 className={styles.boardListItemTitle}>{"오늘 점심 뭐먹지 맛있는 거 먹고 싶은데 추천 부탁"}</h2>
        <p className={styles.boardListItemContent}>
          {"오늘 점심 뭐먹지 맛있는 거 먹고 싶은데 추천 부탁..."}
        </p>
      </section>
      <div className={styles.boardListItemCountsBox}>
        <p className={styles.boardListItemCounts}>{"댓글 0 • 좋아요 0 • 조회수 0"}</p>
      </div>
    </article>
  )
}

export default BoardListItem;
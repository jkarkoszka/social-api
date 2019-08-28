package com.jkarkoszka.socialapi.post;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface PostRepository extends PagingAndSortingRepository<Post, ObjectId> {

    Page<Post> findByUserIdOrderByCreatedDateDesc(ObjectId id, Pageable pageable);

    Page<Post> findByUserIdInOrderByCreatedDateDesc(Set<ObjectId> ids, Pageable pageable);
}

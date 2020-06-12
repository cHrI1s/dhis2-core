package org.hisp.dhis.webapi.controller.dataitem;

/*
 * Copyright (c) 2004-2020, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.junit.MockitoJUnit.rule;

import java.util.List;

import org.hisp.dhis.cache.CacheProvider;
import org.hisp.dhis.common.BaseDimensionalItemObject;
import org.hisp.dhis.fieldfilter.FieldFilterService;
import org.hisp.dhis.node.types.CollectionNode;
import org.hisp.dhis.node.types.RootNode;
import org.hisp.dhis.query.QueryService;
import org.hisp.dhis.webapi.service.LinkService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.springframework.core.env.Environment;

public class ResponseHandlerTest
{

    @Mock
    private QueryService queryService;

    @Mock
    private LinkService linkService;

    @Mock
    private FieldFilterService fieldFilterService;

    @Mock
    private DataItemServiceFacade dataItemServiceFacade;

    @Mock
    private Environment environment;

    @Mock
    private CacheProvider cacheProvider;

    @Rule
    public MockitoRule mockitoRule = rule();

    @Rule
    public ExpectedException expectedException = none();

    private ResponseHandler responseHandler;

    @Before
    public void setUp()
    {
        responseHandler = new ResponseHandler( queryService, linkService, fieldFilterService,
            dataItemServiceFacade, environment, cacheProvider );
    }

    @Test
    public void testAddResultsToNode()
    {
        // Given
        final RootNode anyRootNode = new RootNode( "any" );
        final List<BaseDimensionalItemObject> anyDimensionalItems = asList( new BaseDimensionalItemObject( "any" ) );
        final List<String> anyFields = asList( "any" );
        final CollectionNode anyCollectionNode = new CollectionNode( "any" );

        // When
        when( fieldFilterService.toCollectionNode( any(), any() ) ).thenReturn( anyCollectionNode );
        responseHandler.addResultsToNode( anyRootNode, anyDimensionalItems, anyFields );

        // Then
        assertThat( anyRootNode, is( notNullValue() ) );
        assertThat( anyRootNode.getName(), is( equalTo( "any" ) ) );
        assertThat( anyRootNode.getChildren(), hasSize( 1 ) );
        assertThat( anyRootNode.getChildren().get( 0 ).isCollection(), is( true ) );
    }

    @Test
    public void addPaginationToNode()
    {
    }
}
